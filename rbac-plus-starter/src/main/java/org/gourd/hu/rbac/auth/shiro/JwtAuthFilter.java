package org.gourd.hu.rbac.auth.shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.holder.SpringContextHolder;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.constant.HeaderConstant;
import org.gourd.hu.core.utils.JsonConvertUtil;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.JwtConstant;
import org.gourd.hu.rbac.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * @author gourd.hu
 */
@Component
@Slf4j
public class JwtAuthFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private AuthProperties authProperties;

    /**
     * 判断用户是否想要登入
     * 检测header里面是否包含token即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        if(authProperties == null ){
            authProperties = SpringContextHolder.getBean("authProperties",AuthProperties.class);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String jwtToken = req.getHeader(authProperties.getJwt().getHeader());
        return StringUtils.isNotEmpty(jwtToken);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String accessToken = httpServletRequest.getHeader(authProperties.getJwt().getHeader());
        JwtToken jwtToken = new JwtToken(accessToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西 所以我们在这里返回true，Controller中可以通过
     * subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 查看当前Header中是否携带Authorization属性(Token)，有的话就进行登录认证授权
        if (this.isLoginAttempt(request, response)) {
            try {
                // 进行Shiro的登录UserRealm
                this.executeLogin(request, response);
            } catch (Exception e) {
                // 认证出现异常，传递错误信息msg
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable instanceof SignatureVerificationException) {
                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable instanceof TokenExpiredException) {
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    // 应用异常不为空
                    if (throwable != null) {
                        // 获取应用异常msg
                        msg = throwable.getMessage();
                    }
                }
                // Token认证失败直接返回Response信息
                this.response401(response, msg);
                return false;
            }
        } else {
            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 如果是feign调用不行鉴权
            String tokenIgnoreFlag = httpServletRequest.getHeader(HeaderConstant.HEADER_TOKEN_IGNORE);
            if(HeaderConstant.TOKEN_IGNORE_FLAG.equals(tokenIgnoreFlag)){
                return true;
            }
            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();
            // 获取当前请求URI
            String requestURI = httpServletRequest.getRequestURI();
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, httpMethod);
            this.response401(response, "请先登录");
            return false;
        }
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(authProperties.getJwt().getHeader());
        // 获取当前userId
        String subject = JwtUtil.getSubject(token);
        // 判断Redis中RefreshToken是否存在
        if (RedisUtil.existAny(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + subject)) {
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            Long currentTimeMillisRedis = Long.valueOf(RedisUtil.get(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + subject).toString());
            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (JwtUtil.getClaimLong(token,JwtConstant.JWT_CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                // 获取当前最新时间戳
                Long currentTimeMillis = System.currentTimeMillis();
                // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                RedisUtil.setExpire(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + subject, currentTimeMillis, authProperties.getJwt().getRefreshTokenExpireTime());
                // 刷新AccessToken，设置时间戳为当前最新时间戳
                token = JwtUtil.refreshToken(token,currentTimeMillis);
                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);
                // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                this.getSubject(request, response).login(jwtToken);
                // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader(authProperties.getJwt().getHeader(), token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", authProperties.getJwt().getHeader());
                return true;
            }
        }
        return false;
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            String data = JsonConvertUtil.objectToJson( BaseResponse.fail(ResponseEnum.UNAUTHORIZED, "无权访问(Unauthorized):" + msg));
            out.append(data);
        } catch (IOException e) {
            log.error("直接返回Response信息出现IOException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("直接返回Response信息出现IOException异常");
        }
    }
}

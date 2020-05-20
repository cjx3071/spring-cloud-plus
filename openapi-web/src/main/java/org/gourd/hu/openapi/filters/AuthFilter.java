package org.gourd.hu.openapi.filters;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.utils.IpAddressUtil;
import org.gourd.hu.openapi.constant.AuthConstant;
import org.gourd.hu.openapi.dao.SysSecretDao;
import org.gourd.hu.openapi.entity.SysSecret;
import org.gourd.hu.openapi.utils.SignUtil;
import org.gourd.hu.openapi.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义拦截器，验证第三方是否有权限访问接口
 *
 * @author chason
 * @date 2018-03-10
 */
@Component
@Slf4j
public class AuthFilter implements Filter, Ordered {

    @Autowired
    private SysSecretDao sysSecretDao;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 将请求转换成utf-8编码
        request.setCharacterEncoding("UTF-8");
        String requestMethod = request.getMethod();
        String requestUrL = request.getRequestURL().toString();
        if(request.getServletPath().contains("/actuator") || "/".equals(request.getServletPath())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        log.info("请求方法为：{}，请求路径为：{}", requestMethod, requestUrL);
        // 校验基本参数

        String sign = request.getHeader(AuthConstant.SIGN_KEY);
        // 断言签名不为空
        ResponseEnum.BAD_REQUEST.assertNotEmpty(sign);
        String appKey = request.getHeader(AuthConstant.ACCESS_KEY);
        // 断言appKey不为空
        ResponseEnum.BAD_REQUEST.assertNotEmpty(sign);
        Long timestamp = Long.valueOf(request.getHeader(AuthConstant.TIMESTAMP_KEY));
        // 断言时间戳不为空
        ResponseEnum.BAD_REQUEST.assertNotNull(timestamp);
        String nonce = request.getHeader(AuthConstant.NONCE_KEY);
        // 断言随机数不为空
        ResponseEnum.BAD_REQUEST.assertNotNull(nonce);
        // 断言随机数未使用过
        ResponseEnum.BAD_REQUEST.assertIsTrue(RedisUtil.existAny(IpAddressUtil.getIpAddr(request)+nonce));
        // 将请求字符串转换成Map
        Map<String, String> queryStrMap = null;
        if(HttpMethod.GET.toString().equals(requestMethod)){
            queryStrMap = UrlUtil.getGetParameterMap(request);
        }else {
            JSONObject jsonObject = UrlUtil.getPostParameter(request);
            queryStrMap =UrlUtil.getPostParameterMap(jsonObject);
        }
        queryStrMap.put(AuthConstant.TIMESTAMP_KEY,timestamp.toString());
        // 获取数据库密钥信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("app_key",appKey);
        SysSecret sysSecret = sysSecretDao.selectOne(queryWrapper);
        // 断言appKey存在
        ResponseEnum.APP_KEY_ERROR.assertNotNull(sysSecret);
        // 校验过期时间
        long curr = System.currentTimeMillis();
        // 断言过期时间未过期
        ResponseEnum.SIGN_EXPIRED.assertIsFalse((curr - timestamp) < 0 || (curr - timestamp) > sysSecret.getExpireTimes());
        // 将签名，appKey、secret加入
        queryStrMap.put(AuthConstant.SIGN_KEY,sign);
        queryStrMap.put(AuthConstant.ACCESS_KEY,appKey);
        queryStrMap.put(AuthConstant.SECRET_KEY,sysSecret.getSecretKey());
        // 断言签名正确
        ResponseEnum.SIGN_ERROR.assertIsTrue(SignUtil.verify(queryStrMap));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

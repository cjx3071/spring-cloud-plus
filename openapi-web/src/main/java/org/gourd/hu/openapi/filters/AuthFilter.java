package org.gourd.hu.openapi.filters;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.common.exception.BusinessException;
import org.gourd.hu.base.utils.IpAddressUtil;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.openapi.constant.AuthConstant;
import org.gourd.hu.openapi.dao.SysSecretDao;
import org.gourd.hu.openapi.entity.SysSecret;
import org.gourd.hu.openapi.utils.SignUtil;
import org.gourd.hu.openapi.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
        log.info("请求方法为：{}，请求路径为：{}", requestMethod, requestUrL);
        // 校验基本参数
        if (StringUtils.isBlank(request.getHeader(AuthConstant.SIGN_KEY))) {
            log.info("签名不能为空");
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),AuthConstant.PARAM_ERROR);
        }
        String sign = request.getHeader(AuthConstant.SIGN_KEY);
        if(StringUtils.isBlank(request.getHeader(AuthConstant.ACCESS_KEY))){
            log.info("appKey不能为空");
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),AuthConstant.PARAM_ERROR);
        }
        String appKey = request.getHeader(AuthConstant.ACCESS_KEY);
        if (StringUtils.isBlank(request.getHeader(AuthConstant.TIMESTAMP_KEY)) ) {
            log.info("时间戳不能为空");
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),AuthConstant.PARAM_ERROR);
        }
        Long timestamp = Long.valueOf(request.getHeader(AuthConstant.TIMESTAMP_KEY));
        String nonce = request.getHeader(AuthConstant.NONCE_KEY);
        if(StringUtils.isBlank(request.getHeader(AuthConstant.NONCE_KEY))){
            log.info("随机数不能为空");
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),AuthConstant.PARAM_ERROR);
        }
        if(!RedisUtil.exists(IpAddressUtil.getIpAddr(request)+nonce)){
            log.info("随机数已使用过");
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),AuthConstant.SIGN_EXPIRED);
        }
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
        if(sysSecret == null){
            log.info("appKey不存在");
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(),AuthConstant.APP_KEY_ERROR);
        }
        // 校验过期时间
        long curr = System.currentTimeMillis();
        if ((curr - timestamp) < 0 || (curr - timestamp) > sysSecret.getExpireTimes()){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(),AuthConstant.SIGN_EXPIRED);
        }
        // 将签名，appKey、secret加入
        queryStrMap.put(AuthConstant.SIGN_KEY,sign);
        queryStrMap.put(AuthConstant.ACCESS_KEY,appKey);
        queryStrMap.put(AuthConstant.SECRET_KEY,sysSecret.getSecretKey());
        // 校验签名
        if(!SignUtil.verify(queryStrMap)){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), AuthConstant.SIGN_ERROR);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

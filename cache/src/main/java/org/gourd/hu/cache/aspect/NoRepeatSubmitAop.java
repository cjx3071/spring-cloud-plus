package org.gourd.hu.cache.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.gourd.hu.base.common.exception.BusinessException;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.cache.annotation.NoRepeatSubmit;
import org.gourd.hu.cache.utils.Md5Util;
import org.gourd.hu.cache.utils.RedisUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口防重复切面
 * @author gourd
 * @date 2018-08-26
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAop {

    private static final String JWT_TOKEN_KEY = "jwt-token";

    @Pointcut("@annotation(org.gourd.hu.cache.annotation.NoRepeatSubmit)")
    public void serviceNoRepeat() {
    }

    @Around("serviceNoRepeat()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = RequestHolder.getRequest();
        String jwtToken = request.getHeader(JWT_TOKEN_KEY);
        String key = Md5Util.md5(jwtToken + "-" + request.getRequestURL().toString()+"-"+ JSON.toJSONString(request.getParameterMap()));
        if (RedisUtil.get(key) == null) {
            Object o = pjp.proceed();
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            NoRepeatSubmit noRepeatSubmit = signature.getMethod().getAnnotation(NoRepeatSubmit.class);
            // 默认1秒内统一用户同一个地址同一个参数，视为重复提交
            RedisUtil.setExpire(key, "0",noRepeatSubmit.time());
            return o;
        } else {
            throw new BusinessException("重复提交");
        }
    }

}
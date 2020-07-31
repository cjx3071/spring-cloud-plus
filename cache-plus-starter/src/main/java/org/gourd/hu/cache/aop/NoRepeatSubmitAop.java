package org.gourd.hu.cache.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.cache.annotation.NoRepeatSubmit;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.utils.Md5Util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * 接口防重复切面
 * @author gourd.hu
 * @date 2018-08-26
 */
@Aspect
@Slf4j
public class NoRepeatSubmitAop {

    private static final String JWT_TOKEN_KEY = "jwt-token";
    public static final String GUEST = "guest";

    @Pointcut("execution(* org.gourd.hu.*.controller.*Controller.*(..))")
    public void serviceNoRepeat() {
    }

    @Around("serviceNoRepeat()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = RequestHolder.getRequest();
        String jwtToken = StringUtils.isBlank(request.getHeader(JWT_TOKEN_KEY)) ? GUEST : request.getHeader(JWT_TOKEN_KEY);
        // 需要拦截的方法类型
        List<String> methods = Arrays.asList("POST", "PUT", "DELETE", "PATCH");
        // 方法类型
        String method = request.getMethod();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 接口上加的注解
        NoRepeatSubmit noRepeatSubmit = signature.getMethod().getAnnotation(NoRepeatSubmit.class);
        if(methods.contains(method)){
            // 请求体参数
            String postData = this.getBody(request);
            String key = Md5Util.md5(jwtToken + "-" + request.getRequestURL().toString()+"-"+ postData);
            if (RedisUtil.get(key) == null) {
                // 默认1秒内统一用户同一个地址同一个参数，视为重复提交
                RedisUtil.setExpire(key, "0",noRepeatSubmit == null ? 1 : noRepeatSubmit.value());
            } else {
                throw  ResponseEnum.REPEAT_COMMIT.newException();
            }
        }
        return pjp.proceed();
    }


    /**
     * 获取请求体
     *
     * @param request
     * @return
     */
    private String getBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader isr = null;
        try(
                InputStream ins = request.getInputStream()){
            if(ins != null){
                isr = new BufferedReader(new InputStreamReader(ins));
                char[] charBuffer = new char[128];
                int readCount;
                while((readCount = isr.read(charBuffer)) != -1){
                    sb.append(charBuffer,0,readCount);
                }
            }
        }catch (
                IOException e){
            log.error(e.getMessage(),e);
        }finally {
            if(isr != null) {
                isr.close();
            }
        }
        return sb.toString();
    }
}
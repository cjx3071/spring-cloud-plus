package org.gourd.hu.log.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.gourd.hu.log.annotation.OperateLogIgnore;
import org.gourd.hu.log.entity.SysOperateLog;
import org.gourd.hu.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 拦截所有方法AOP
 * @author gourd
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
@ConditionalOnProperty(name = "logging.operate.all",havingValue = "true",matchIfMissing = true)
public class LogAllAop {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 配置切入点
     */
    @Pointcut("execution(* org.gourd.hu.*.controller..*.*(..))")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        Object result = null;
        // 开始时间
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 操作日志忽略
        OperateLogIgnore operateLogIgnore = signature.getMethod().getAnnotation(OperateLogIgnore.class);
        SysOperateLog sysLog = new SysOperateLog();
        try {
            result = joinPoint.proceed();
            // 返回结果
            sysLog.setResponseDetail(JSON.toJSONString(result));
        } catch (Throwable e) {
            // 异常信息
            sysLog.setExceptionDetail(JSON.toJSONString(e));
            throw new RuntimeException(e.getMessage(),e.getCause());
        }finally {
            if(operateLogIgnore ==null){
                operateLogService.asyncSaveLog(joinPoint, startTime, sysLog);
            }
        }
        return result;
    }
}

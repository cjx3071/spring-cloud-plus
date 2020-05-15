package org.gourd.hu.log.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.log.entity.SysOperateLog;
import org.gourd.hu.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.servlet.http.HttpServletRequest;

/**
 * 指定日志注解AOP
 * @author gourd
 * @date 2018-11-24
 */
@Aspect
@Slf4j
@ConditionalOnProperty(name = "logging.operate.all",havingValue = "false")
public class LogPointAop {
    @Autowired
    private OperateLogService operateLogService;

    private static ThreadLocal<ProceedingJoinPoint> proceedingJoinPointThreadLocal = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(org.gourd.hu.log.annotation.OperateLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        proceedingJoinPointThreadLocal.set(joinPoint);
        HttpServletRequest request = RequestHolder.getRequest();
        // 开始时间
        long startTime = System.currentTimeMillis();
        SysOperateLog sysLog = new SysOperateLog();
        Object result = joinPoint.proceed();
        // 返回结果
        sysLog.setResponseDetail(JSON.toJSONString(result));
        operateLogService.asyncSaveLog(request,joinPoint, startTime, sysLog);
        return result;
    }

    @AfterThrowing(pointcut="logPointcut()",throwing = "exception")
    public void afterThrowing(Exception exception){
        ProceedingJoinPoint proceedingJoinPoint = proceedingJoinPointThreadLocal.get();
        HttpServletRequest request = RequestHolder.getRequest();
        SysOperateLog sysLog = new SysOperateLog();
        // 异常信息
        sysLog.setExceptionDetail(JSON.toJSONString(exception));
        operateLogService.asyncSaveLog(request,proceedingJoinPoint, null, sysLog);
    }

}

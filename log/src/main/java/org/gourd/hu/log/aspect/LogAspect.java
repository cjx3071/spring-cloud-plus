package org.gourd.hu.log.aspect;

import com.alibaba.fastjson.JSON;
import org.gourd.hu.base.common.exception.BusinessException;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.base.utils.DateUtil;
import org.gourd.hu.base.utils.IpAddressUtil;
import org.gourd.hu.log.annotation.Log;
import org.gourd.hu.log.entity.LogPO;
import org.gourd.hu.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author gourd
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * 默认30天
     */
    @Value("${log.expire.days:30}")
    private int expireDays;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(org.gourd.hu.log.annotation.Log)")
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
        HttpServletRequest request = RequestHolder.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log aopLog = signature.getMethod().getAnnotation(Log.class);
        LogPO logPO = new LogPO();
        try {
            result = joinPoint.proceed();
            // 返回结果
            logPO.setResponseDetail(JSON.toJSONString(result));
        } catch (Throwable e) {
            // 异常信息
            logPO.setExceptionDetail(JSON.toJSONString(e));
            throw new BusinessException(e.getMessage());
        }finally {
            // 设置过期时间
            Date expireTime = DateUtil.addDaysForDate(DateUtil.getBeginTimeOfToday(), expireDays);
            logPO.setExpireTime(expireTime);
            // 单位毫秒
            Long requestTime = (System.currentTimeMillis() - startTime);
            // 描述
            logPO.setDescription(aopLog.description());
            // 模块
            logPO.setModuleName(aopLog.moduleName());
            // 业务
            logPO.setBusinessName(aopLog.businessName());
            // 方法名
            logPO.setMethodName(signature.getName());
            // 方法类型
            logPO.setMethodType(request.getMethod());
            // 请求路径
            logPO.setRequestUrl(request.getRequestURL().toString());
            // 请求参数
            logPO.setRequestParam(getParam(joinPoint, signature));
            // 获取用户ip
            logPO.setRequestIp(IpAddressUtil.getIpAddr(request));
            // 请求耗时
            logPO.setRequestTime(requestTime);
            logService.save(logPO);
        }
        return result;
    }


    /**
     * 获取请求参数
     * @param joinPoint
     * @param signature
     * @return
     */
    private String getParam(JoinPoint joinPoint, MethodSignature signature) {
        StringBuilder params = new StringBuilder("{");
        // 参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = signature.getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ");
                params.append(argNames[i]).append(":");
                params.append(argValues[i]);
            }
        }
        params.append(" }");
        return params.toString();
    }
}

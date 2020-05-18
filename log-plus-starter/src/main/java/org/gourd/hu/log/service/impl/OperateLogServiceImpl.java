package org.gourd.hu.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.gourd.hu.core.utils.IpAddressUtil;
import org.gourd.hu.log.annotation.OperateLog;
import org.gourd.hu.log.dao.SysOperateLogDao;
import org.gourd.hu.log.entity.SysOperateLog;
import org.gourd.hu.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author gourd.hu
 * @date 2018-11-24
 */
@Slf4j
public class OperateLogServiceImpl extends ServiceImpl<SysOperateLogDao, SysOperateLog> implements OperateLogService {

    @Resource
    private SysOperateLogDao sysOperateLogDao;

    /**
     * 默认30天
     */
    @Value("${logging.operate.expire.days:30}")
    private int expireDays;

    @Override
    public void deleteLogs(List<Long> ids){
        sysOperateLogDao.deleteByIds(ids);
    }

    /**
     * 异步保存日志信息
     *
     * @param joinPoint
     * @param startTime
     * @param sysOperateLog
     */
    @Async
    @Override
    public void asyncSaveLog(HttpServletRequest request,ProceedingJoinPoint joinPoint,Long startTime, SysOperateLog sysOperateLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperateLog operateLog = signature.getMethod().getAnnotation(OperateLog.class);
        if(operateLog != null){
            // 描述
            sysOperateLog.setDescription(operateLog.description());
            // 模块
            sysOperateLog.setModuleName(operateLog.moduleName());
            // 业务
            sysOperateLog.setBusinessName(operateLog.businessName());
        }
        // 设置过期时间
        LocalDateTime expireTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)).plusDays(expireDays);
        sysOperateLog.setExpireTime(expireTime);
        // 单位毫秒
        Long requestTime =startTime ==null? null: (System.currentTimeMillis() - startTime);
        // 方法名
        sysOperateLog.setMethodName(signature.getName());
        // 方法类型
        sysOperateLog.setMethodType(request.getMethod());
        // 请求路径
        sysOperateLog.setRequestUrl(request.getRequestURL().toString());
        // 请求参数
        sysOperateLog.setRequestParam(getParam(joinPoint, signature));
        // 获取用户ip
        sysOperateLog.setRequestIp(IpAddressUtil.getIpAddr(request));
        // 请求耗时
        sysOperateLog.setRequestTime(requestTime);
        sysOperateLogDao.insert(sysOperateLog);
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

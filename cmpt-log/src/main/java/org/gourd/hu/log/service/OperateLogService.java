package org.gourd.hu.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.gourd.hu.log.entity.SysOperateLog;

import java.util.List;

/**
 * @author gourd
 * @date 2018-11-24
 */
public interface OperateLogService extends IService<SysOperateLog> {
    /**
     * 物理删除日志
     * @param ids
     */
    void deleteLogs(List<Long> ids);

    /**
     * 异步插入日志
     * @param joinPoint
     * @param startTime
     * @param sysOperateLog
     */
    void asyncSaveLog(ProceedingJoinPoint joinPoint, long startTime, SysOperateLog sysOperateLog);
}

package org.gourd.hu.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * job触发时间
 * @author gourd
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DemoJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        log.info(LocalDateTime.now() + "DemoJob执行");
        // 获取参数
        JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
    }

}
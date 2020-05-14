package org.gourd.hu.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * job触发时间
 * @author gourd
 */
@Component
@Slf4j
public class DemoJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        log.info(LocalDateTime.now() + "DemoJob执行");
        // 获取参数
        JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
    }

}
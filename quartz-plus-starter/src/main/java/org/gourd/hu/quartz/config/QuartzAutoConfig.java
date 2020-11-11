package org.gourd.hu.quartz.config;

import org.gourd.hu.quartz.job.DemoJob;
import org.gourd.hu.quartz.job.DynamicDemoTask;
import org.gourd.hu.quartz.job.SpringDemoTask;
import org.gourd.hu.quartz.properties.QuartzDataSourceProperties;
import org.gourd.hu.quartz.service.impl.QuartzServiceImpl;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.TimeZone;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@EnableScheduling
@Import({QuartzDataSourceProperties.class,DemoJob.class, DynamicDemoTask.class, SpringDemoTask.class, QuartzServiceImpl.class})
public class QuartzAutoConfig {

    public static final String SYS_CHINA_TIMEZONE = "Asia/Shanghai";

    @Autowired
    private QuartzDataSourceProperties quartzDataSourceProperties;

    private DataSource quartzDataSource;

    @Order(1)
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
        quartzDataSource = quartzDataSourceProperties.initializeDataSourceBuilder().build();
        return schedulerFactoryBean -> {
            schedulerFactoryBean.setDataSource(quartzDataSource);
            schedulerFactoryBean.setTransactionManager(new DataSourceTransactionManager(quartzDataSource));
        };
    }


    /**
     * 配置触发器tableKeyTrigger
     * @param tableKeyJobDetail
     * @return
     */
    @Bean("tableKeyTrigger")
    public CronTriggerFactoryBean tableKeyTrigger(JobDetail tableKeyJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(tableKeyJobDetail);
        trigger.setCronExpression("0 0/30 * * * ?");
        trigger.setTimeZone(TimeZone.getTimeZone(SYS_CHINA_TIMEZONE));
        trigger.setName("tableKeyTrigger");
        return trigger;
    }


    /**
     * 配置定时任务tableKeyJobDetail
     * @return
     */
    @Bean("tableKeyJobDetail")
    public JobDetailFactoryBean tableKeyJobDetail() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        // 任务的名字
        jobDetail.setName("demoJob");
        jobDetail.setJobClass(DemoJob.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }


    /**
     * 配置Scheduler
     * @param tableKeyTrigger
     * @return
     */
    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactory(@Qualifier("tableKeyTrigger") Trigger tableKeyTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setSchedulerName("test");
        bean.setDataSource(quartzDataSource);
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        // 注册触发器
        bean.setTriggers(tableKeyTrigger);
        return bean;
    }

}

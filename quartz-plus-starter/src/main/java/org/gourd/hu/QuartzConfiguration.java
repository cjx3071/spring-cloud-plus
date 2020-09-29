package org.gourd.hu;

import com.alibaba.druid.pool.DruidDataSource;
import org.gourd.hu.quartz.job.DemoJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class QuartzConfiguration {

    public static final String SYS_CHINA_TIMEZONE = "Asia/Shanghai";

    /**
     * 配置Quartz独立数据源的配置
     */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.quartz")
    public DataSource quartzDataSource(){
        return new DruidDataSource();
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
        bean.setDataSource(this.quartzDataSource());
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        // 注册触发器
        bean.setTriggers(tableKeyTrigger);
        return bean;
    }

}

package org.gourd.hu.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 可动态修改的定时任务
 * @author gourd
 */
@Slf4j
public class DynamicDemoTask implements SchedulingConfigurer {
 
    /**
     * cron表达式（每月1号零点执行）
     */
    private  String cron="0 0 0 1 * ?";
    /**
     * 任务名称
     */
    private String name="gourd_schedule";
    /**
     * 自定义参数
     */
    private String jobData="";

    @Value("${openOffice.windowsFileTempLoc}")
    private String windowsFileTempLoc;

    @Value("${openOffice.linuxFileTempLoc}")
    private String linuxFileTempLoc;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getCron() {
        return cron;
    }
 
    public void setCron(String cron) {
        this.cron = cron;
    }
 
    public String getJobData() {
        return jobData;
    }
 
    public void setJobData(String jobData) {
        this.jobData = jobData;
    }
 
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(doTask(jobData), getTrigger());
    }
 
    /**
     * 业务执行方法
     * @return
     */
    private Runnable doTask(String jobData) {
        return new Runnable() {
            @Override
            public void run() {
                log.info("^o^= jobData"+jobData);
                // 清空临时文件夹文件
                String tempPath = getTempPath();
                if(StringUtils.isNotEmpty(tempPath)){
                    boolean flag = deleteFiles(tempPath);
                    if(flag){
                        log.info("删除临时文件夹目录成功");
                    }else {
                        log.info("删除临时文件夹目录失败");
                    }
                }else {
                    log.info("未获取到临时文件夹目录");
                }
            }
        };
    }
 
    /**
     * 业务触发器
     * @return
     */
    private Trigger getTrigger() {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 触发器
                CronTrigger trigger = new CronTrigger(cron);
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }

    /**
     * 清除临时文件夹中的文件。
     */
    protected boolean deleteFiles(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                deleteFiles(path + "/" + tempList[i]);
                // 再删除空文件夹
                deleteFiles(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 判断系统环境，并获得临时文件夹目录
     *
     * @return
     */
    public String getTempPath() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
           return linuxFileTempLoc;
        } else if (Pattern.matches("Windows.*", osName)) {
            return windowsFileTempLoc;
        } else if (Pattern.matches("Mac.*", osName)) {
            return windowsFileTempLoc;
        }
        return null;
    }
}
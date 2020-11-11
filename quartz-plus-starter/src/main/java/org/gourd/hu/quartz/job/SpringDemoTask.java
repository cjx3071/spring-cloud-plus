package org.gourd.hu.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author gourd
 */
@Slf4j
public class SpringDemoTask {
    @Value("${schedule.task.run-host}")
    private String runHost;

    /**
     * spring task
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void doTask() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();

        // 定时器逻辑只在指定ip地址执行
        if(!ip.equals(runHost)){
            return;
        }
        log.info(">o< 定时任务执行： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "  "+ Thread.currentThread().getName());
    }
}

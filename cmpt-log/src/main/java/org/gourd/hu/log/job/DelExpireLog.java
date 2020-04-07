package org.gourd.hu.log.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.gourd.hu.log.entity.LogPO;
import org.gourd.hu.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清除过期日志记录
 * @author gourd
 */
@Component
@Slf4j
public class DelExpireLog {

    @Autowired
    private LogService logService;

    /**
     * 每天0点执行一次删除过期日志
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doTask(){
        log.info(">o< 删除过期日志定时任务执行： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  "+ Thread.currentThread().getName());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.le("expire_time",new Date());
        List<LogPO> logPOS = logService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(logPOS)){
            logService.deleteLogs(logPOS.stream().map(e-> e.getId()).collect(Collectors.toList()));
        }
        log.info(">o< 删除过期日志定时任务结束： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  "+ Thread.currentThread().getName());
    }
}

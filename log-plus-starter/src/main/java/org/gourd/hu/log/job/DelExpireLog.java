package org.gourd.hu.log.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.log.entity.SysOperateLog;
import org.gourd.hu.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清除过期日志记录
 * @author gourd.hu
 */
@Slf4j
public class DelExpireLog {

    @Resource
    private OperateLogService operateLogService;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    /**
     * 每天0点执行一次删除过期日志
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doTask(){
        log.info(">o< 删除过期日志定时任务开始执行： "+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "  "+ Thread.currentThread().getName());
        while(true){
            Page page = new Page<>(1,500);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.le("expire_time", LocalDateTime.now());
            IPage<SysOperateLog> logIPage= operateLogService.page(page, queryWrapper);
            List<SysOperateLog> logPOS = logIPage.getRecords();
            if(CollectionUtils.isEmpty(logPOS)){
                break;
            }
            List<Long> logIds = logPOS.stream().map(e -> e.getId()).collect(Collectors.toList());
            asyncTaskExecutor.execute(() -> operateLogService.deleteLogs(logIds));
        }
        log.info(">o< 删除过期日志定时任务结束： "+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "  "+ Thread.currentThread().getName());
    }
}

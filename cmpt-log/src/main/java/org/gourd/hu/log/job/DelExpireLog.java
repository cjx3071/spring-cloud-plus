package org.gourd.hu.log.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gourd.hu.base.utils.PageUtil;
import org.gourd.hu.log.entity.SysOperateLog;
import org.gourd.hu.log.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * 清除过期日志记录
 * @author gourd
 */
@Component
@Slf4j
public class DelExpireLog {

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private Executor asyncExecutor;

    /**
     * 每天0点执行一次删除过期日志
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doTask(){
        log.info(">o< 删除过期日志定时任务开始执行： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  "+ Thread.currentThread().getName());
        while(true){
            Page page = new Page<>(1,1000);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.le("expire_time",new Date());
            IPage<SysOperateLog> logIPage= operateLogService.page(page, queryWrapper);
            List<SysOperateLog> logPOS = logIPage.getRecords();
            if(CollectionUtils.isEmpty(logPOS)){
                break;
            }
            List<Long> logIds = logPOS.stream().map(e -> e.getId()).collect(Collectors.toList());
            asyncExecutor.execute(() -> operateLogService.deleteLogs(logIds));
        }
        log.info(">o< 删除过期日志定时任务结束： "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  "+ Thread.currentThread().getName());
    }
}

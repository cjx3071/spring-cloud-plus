package org.gourd.hu.log.config;

import lombok.Data;
import org.gourd.hu.log.aop.LogAllAop;
import org.gourd.hu.log.aop.LogPointAop;
import org.gourd.hu.log.job.DelExpireLog;
import org.gourd.hu.log.service.impl.OperateLogServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 日志配置
 *
 * @author gourd.hu
 */

@Data
@Configuration
@EnableScheduling
@MapperScan({"org.gourd.hu.log.dao"})
@Import({LogAllAop.class, LogPointAop.class, DelExpireLog.class, OperateLogServiceImpl.class})
public class LogAutoConfig  {

}
package org.gourd.hu;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {


    /**
     * 配置Quartz独立数据源的配置
     */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.quartz")
    public DruidDataSource quartzDataSource(){
        return new DruidDataSource();
    }

}

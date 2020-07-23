package org.gourd.hu.activiti.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

/**
 * activiti配置
 *
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@SpringBootApplication(exclude={
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.activiti")
    public DruidDataSource activitiDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        return baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                transactionManager,
                springAsyncExecutor);
    }

}

package org.gourd.hu.activiti.config;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.gourd.hu.activiti.properties.ActivitiDataSourceProperties;
import org.gourd.hu.activiti.service.impl.WorkFlowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
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
@Import({ActivitiDataSourceProperties.class, WorkFlowServiceImpl.class})
public class ActivitiAutoConfig extends AbstractProcessEngineAutoConfiguration {

    @Autowired
    private ActivitiDataSourceProperties activitiDataSourceProperties;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        DataSource dataSource = activitiDataSourceProperties.initializeDataSourceBuilder().build();
        return baseSpringProcessEngineConfiguration(
                dataSource,
                transactionManager,
                springAsyncExecutor);
    }

}

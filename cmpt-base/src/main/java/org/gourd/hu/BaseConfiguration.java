package org.gourd.hu;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author gourd
 * @date 2018-11-20
 */
@Configuration
@EnableTransactionManagement
@EnableSwagger2
@MapperScan({"org.gourd.hu.*.dao"})
@EntityScan
public class BaseConfiguration {

    /**
     * 主数据源
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 不依赖cloud模块需要注册此bean
     * @return
     */
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }



}

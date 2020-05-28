package org.gourd.hu.admin.config;

import org.gourd.hu.base.config.Swagger2Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger接口文档
 *
 * @author gourd.hu
 */
@Configuration
@EnableSwagger2
public class AdminSwagger2Config {

    @Bean
    public Docket gourdHuWeb() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin项目接口文档")
                .apiInfo(Swagger2Config.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.gourd.hu.admin.controller"))
                .paths(PathSelectors.any()).build();
    }
}
package org.gourd.hu.rbac.config;

import org.gourd.hu.base.config.Swagger2Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger接口文档
 * 访问：http://localhost:10001/doc.html#/
 *
 * @author gourd.hu
 */
@Configuration
public class RbacSwagger2Config {

    @Bean
    public Docket gourdHuRbac() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户权限接口文档")
                .apiInfo(Swagger2Config.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.gourd.hu.rbac.controller"))
                .paths(PathSelectors.any()).build();
    }
}
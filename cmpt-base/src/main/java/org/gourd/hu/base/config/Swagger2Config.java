package org.gourd.hu.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger接口文档
 * 访问：http://localhost:10001/doc.html#/
 *
 * @author gourd
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket gourdHuDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("模块demo接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.gourd.hu.demo.controller"))
                .paths(PathSelectors.any()).build();
    }

    public static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("葫芦胡系统")
                .description("葫芦胡")
                .termsOfServiceUrl("https://blog.csdn.net/HXNLYW")
                .contact(new Contact("gourd.hu ", "https://blog.csdn.net/HXNLYW", "13584278267@163.com"))
                .version("1.0.0").build();
    }
}
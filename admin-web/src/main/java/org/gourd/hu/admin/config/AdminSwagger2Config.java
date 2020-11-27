package org.gourd.hu.admin.config;

import org.gourd.hu.base.config.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * swagger接口文档
 *
 * @author gourd.hu
 */
@Configuration
@EnableSwagger2WebMvc
public class AdminSwagger2Config {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public Docket gourdHuWeb() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin项目接口文档")
                .apiInfo(new ApiInfoBuilder()
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                        .contact(new Contact(properties.getContact().getName(), properties.getContact().getUrl(), properties.getContact().getEmail()))
                        .version(properties.getVersion()).build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.gourd.hu.admin.controller"))
                .paths(PathSelectors.any()).build();
    }
}
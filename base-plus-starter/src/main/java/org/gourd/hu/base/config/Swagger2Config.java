package org.gourd.hu.base.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * swagger接口文档
 * 访问：http://localhost:10001/doc.html#/
 *
 * @author gourd.hu
 */
@EnableSwagger2WebMvc
@EnableKnife4j
@EnableConfigurationProperties({SwaggerProperties.class})
public class Swagger2Config {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public Docket gourdHuDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("模块demo接口文档")
                .apiInfo(new ApiInfoBuilder()
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                        .contact(new Contact(properties.getContact().getName(), properties.getContact().getUrl(), properties.getContact().getEmail()))
                        .version(properties.getVersion()).build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getDemoPackage()))
                .paths(PathSelectors.any()).build();
    }
}
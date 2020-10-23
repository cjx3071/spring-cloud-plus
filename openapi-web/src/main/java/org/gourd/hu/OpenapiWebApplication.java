package org.gourd.hu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 开放平台启动类
 *
 * @author gourd.hu
 */
@SpringBootApplication
@Slf4j
@MapperScan({"org.gourd.hu.openapi.dao"})
public class OpenapiWebApplication {

    public static void main(String[] args) {
        // new application
        new SpringApplicationBuilder()
                .sources(OpenapiWebApplication.class)
                // default properties
                .properties("--spring.profiles.active=local")
                .web(WebApplicationType.SERVLET)
                .run(args);
        log.info(">o< 开放平台服务启动成功！温馨提示：代码千万行，注释第一行，命名不规范，同事泪两行 >o<");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

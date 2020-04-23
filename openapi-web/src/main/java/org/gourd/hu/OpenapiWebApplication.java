package org.gourd.hu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 开放平台启动类
 *
 * @author gourd.hu
 */
@SpringBootApplication
@Slf4j
public class OpenapiWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OpenapiWebApplication.class)
                .properties("--spring.profiles.active=local");
    }

    public static void main(String[] args) {
        SpringApplication.run(OpenapiWebApplication.class, args);
        log.warn(">o< 开放平台服务启动成功！温馨提示：代码千万行，注释第一行，命名不规范，同事泪两行 >o<");
    }

}

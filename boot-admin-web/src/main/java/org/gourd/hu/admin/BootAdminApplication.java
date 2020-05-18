package org.gourd.hu.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * spring boot admin
 * @author gourd.hu
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class BootAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(BootAdminApplication.class)
                // default properties
                .properties("--spring.profiles.active=local")
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}

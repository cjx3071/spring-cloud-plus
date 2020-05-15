package org.gourd.hu;

import com.netflix.loadbalancer.IRule;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.gateway.config.NacosCustomerRule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 网关服务启动类
 *
 * @author gourd.hu
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        // new application
        new SpringApplicationBuilder()
                .sources(GatewayApplication.class)
                // default properties
                .properties("--spring.profiles.active=local")
                .run(args);
        log.info(">o< 网关服务启动成功！温馨提示：代码千万行，注释第一行，命名不规范，同事泪两行 >o<");

    }

    @Bean
    @Scope(value="prototype")
    @LoadBalanced
    public IRule loadBalanceRule(){
        return new NacosCustomerRule();
    }


}

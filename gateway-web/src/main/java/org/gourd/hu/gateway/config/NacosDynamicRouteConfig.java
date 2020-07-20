package org.gourd.hu.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import org.gourd.hu.gateway.reponsitory.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Nacos动态路由配置
 *
 * @author gourd.hu
 */
@Configuration
public class NacosDynamicRouteConfig {

    @Value("${spring.cloud.nacos.config.router-data-id:gateway-router.json}")
    private String routerDataId;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Bean
    public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
        return new NacosRouteDefinitionRepository(routerDataId,publisher, nacosConfigManager);
    }
}
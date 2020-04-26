package org.gourd.hu.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.gourd.hu.gateway.reponsitory.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 *
 * @author gourd.hu
 */
@Configuration
public class DynamicRouteConfig {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Value("${spring.cloud.nacos.config.router-data-id:gateway-router.json}")
    private String routerDataId;

    /**
     * Nacos实现方式
     */
    @Configuration
    public class NacosDynRoute {
        @Autowired
        private NacosConfigProperties nacosConfigProperties;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(routerDataId,publisher, nacosConfigProperties);
        }
    }
}
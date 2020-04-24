package org.gourd.hu.gateway.service;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 动态路由配置
 *
 * @author gourd.hu
 */
@Component
@Slf4j
public class NacosDynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private  NacosConfigProperties nacosConfigProperties;

    @Autowired
    private  RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @Value("${spring.cloud.nacos.config.router-data-id:gateway-router.json}")
    private String routerDataId;


    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = nacosConfigProperties.configServiceInstance();
            configService.addListener(routerDataId, nacosConfigProperties.getGroup(), new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    clearRoute();
                    try {
                        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
                        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                            addRoute(routeDefinition);
                        }
                        publish();
                        log.info("receiveConfigInfo success："+configInfo);
                    } catch (Exception e) {
                        log.error("receiveConfigInfo error", e);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("dynamicRouteByNacosListener error", e);
        }
    }

    private void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            log.error("addRoute error", e);
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
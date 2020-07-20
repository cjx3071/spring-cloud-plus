package org.gourd.hu.gateway.reponsitory;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * nacos路由数据源
 *
 * @author gourd.hu
 */
@Slf4j
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {

    private String routerDataId;

    private ApplicationEventPublisher publisher;

    private NacosConfigManager nacosConfigManager;

    public NacosRouteDefinitionRepository(String routerDataId,ApplicationEventPublisher publisher,NacosConfigManager nacosConfigManager) {
        this.routerDataId = routerDataId;
        this.publisher = publisher;
        this.nacosConfigManager = nacosConfigManager;
        addListener();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            String content = nacosConfigManager.getConfigService().getConfig(routerDataId, nacosConfigManager.getNacosConfigProperties().getGroup(), 5000);
            List<RouteDefinition> routeDefinitions = getListByStr(content);
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            log.error("getRouteDefinitions by nacos error", e);
        }
        return Flux.fromIterable(Collections.EMPTY_LIST);
    }

    /**
     * 增加Nacos配置监听
     */
    private void addListener() {
        try {
            nacosConfigManager.getConfigService().addListener(routerDataId, nacosConfigManager.getNacosConfigProperties().getGroup(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("Nacos配置变化:{}",configInfo);
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
        }
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    private List<RouteDefinition> getListByStr(String content) {
        if (StringUtils.isNotEmpty(content)) {
            return JSONObject.parseArray(content, RouteDefinition.class);
        }
        return new ArrayList<>(0);
    }
}
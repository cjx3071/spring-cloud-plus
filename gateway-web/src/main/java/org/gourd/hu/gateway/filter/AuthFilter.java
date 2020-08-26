package org.gourd.hu.gateway.filter;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.gateway.exception.UnauthorizedException;
import org.gourd.hu.gateway.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 网关做简单的权限验证
 *
 * @author gourd.hu
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    public static final String REQUEST_TIME = "requestTime";

    @Autowired
    private AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> ignores = Arrays.asList(authProperties.getIgnores());
        if(!CollectionUtils.isEmpty(ignores)){
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(authProperties.getJwt().getHeader());
        if (token == null) {
            token = request.getQueryParams().getFirst(authProperties.getJwt().getHeader());
        }
        if (StringUtils.isEmpty(token)) {
            return Mono.error(new UnauthorizedException("请先登录"));
        }
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(authProperties.getJwt().getHeader(), token)
                .build();
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

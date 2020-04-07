package org.gourd.hu.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis集群配置
 *
 * @author gourd
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisClusterProperties {
    /**
     * 节点
     */
    private String[] nodes;

    /**
     * 获取失败 最大重定向次数
     */
    private Integer maxRedirects;

    /**
     * 扫描间隔
     */
    private Integer scanInterval;
}
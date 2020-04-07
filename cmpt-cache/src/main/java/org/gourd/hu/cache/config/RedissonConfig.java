package org.gourd.hu.cache.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.cache.properties.RedisClusterProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置
 *
 * @author gourd
 */
@Configuration
@Data
public class RedissonConfig {

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    @Value("${spring.redis.password:}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        //单节点
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        // 集群模式配置
        config.useClusterServers().setScanInterval(redisClusterProperties.getScanInterval())
                .addNodeAddress(redisClusterProperties.getNodes());
        if (StringUtils.isNotBlank(password)) {
            config.useClusterServers().setPassword(password);
        }
        //添加主从配置
        // config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        return Redisson.create(config);
    }
}
package org.gourd.hu.cache.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.cache.redisson.impl.RedisDistributedLocker;
import org.gourd.hu.cache.utils.RedisLockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * redisson 配置
 *
 * @author gourd.hu
 */

@Data
@EnableConfigurationProperties({RedisProperties.class})
@Import({RedisDistributedLocker.class, RedisLockUtil.class})
public class RedissonAutoConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if(redisProperties.getCluster() != null){
            // 集群模式配置
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().stream().toArray(String[]::new))
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        }else if(redisProperties.getSentinel() != null){
            //添加哨兵配置
            config.useMasterSlaveServers().setMasterAddress(redisProperties.getSentinel().getMaster())
                    .addSlaveAddress(redisProperties.getSentinel().getNodes().stream().toArray(String[]::new))
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        }else {
            //单节点
            config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
package org.gourd.hu.cache.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.Data;
import org.gourd.hu.cache.aop.NoRepeatSubmitAop;
import org.gourd.hu.cache.utils.RedisUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author gourd.hu
 */

@Data
@EnableCaching
@Configuration
@Import({NoRepeatSubmitAop.class, RedisUtil.class})
public class CacheRedisAutoConfig extends CachingConfigurerSupport {

    /**
     * 缓存失效时间
     */
    private Duration timeToLive = Duration.ZERO;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // 设置hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        FastJsonRedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        jsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);
        // 打开autotype功能
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 设置value采用的fastjson的序列化方式
        template.setValueSerializer(jsonRedisSerializer);
        // 设置hash的value采用的fastjson的序列化方式
        template.setHashValueSerializer(jsonRedisSerializer);
        // 设置其他默认的序列化方式为fastjson
        template.setDefaultSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        jsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);
        // 打开autotype功能
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
}
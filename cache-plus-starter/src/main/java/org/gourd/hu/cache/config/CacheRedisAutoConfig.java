package org.gourd.hu.cache.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.cache.aop.NoRepeatSubmitAop;
import org.gourd.hu.cache.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 缓存配置
 *
 * @author gourd.hu
 */

@Data
@Slf4j
@EnableCaching
@Configuration
@Import({NoRepeatSubmitAop.class, RedisUtil.class})
public class CacheRedisAutoConfig extends CachingConfigurerSupport {

    /**
     * 缓存失效时间,单位秒
     */
    @Value("${spring.cache.redis.time-to-live:5}")
    private Integer timeToLive;

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
                .entryTtl(Duration.ofSeconds(timeToLive))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    /**
     * 类名+方法名+参数列表的类型+参数值 再做 哈希散列 作为key
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".");
                sb.append(method.getName());
                for (Object obj : params) {
                    if (obj != null) {
                        sb.append("&").append(obj.getClass().getName());
                        sb.append("#").append(JSON.toJSONString(obj));
                    }
                }
                log.debug("redis cache key str: " + sb.toString());

                log.debug("redis cache key sha1DigestAsHex: " + DigestUtils.sha1DigestAsHex(sb.toString()));
                return "METHOD-CACHE-" + DigestUtils.sha1DigestAsHex(sb.toString());
            }
        };
    }
}
package org.gourd.hu.rbac.auth.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * 重写Shiro缓存管理器
 * @author gourd.hu
 * @date 2018/9/4 17:41
 */
@Component
public class ShiroCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroCache<K,V>();
    }
}

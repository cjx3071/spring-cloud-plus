package org.gourd.hu.rbac.auth.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.gourd.hu.base.holder.SpringContextHolder;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.JwtConstant;
import org.gourd.hu.rbac.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 * @author gourd.hu
 * @date 2018/9/4 17:31
 */
@Component
public class ShiroCache<K,V> implements Cache<K,V> {

    @Autowired
    private AuthProperties authProperties;

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     */
    private String getKey(Object key) {
        return JwtConstant.PREFIX_SHIRO_ACCESS_TOKEN + JwtUtil.getSubject(key.toString());
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(Boolean.FALSE.equals(RedisUtil.existAny(this.getKey(key)))){
            return null;
        }
        return RedisUtil.get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        if(authProperties == null){
            authProperties= SpringContextHolder.getBean("authProperties",AuthProperties.class);
        }
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        Long shiroCacheExpireTime = authProperties.getJwt().getShiroCacheExpireTime();
        // 设置Redis的Shiro缓存
        RedisUtil.setExpire(this.getKey(key), value, shiroCacheExpireTime);
        return value;
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(Boolean.FALSE.equals(RedisUtil.existAny(this.getKey(key)))){
            return null;
        }
        RedisUtil.del(this.getKey(key));
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}

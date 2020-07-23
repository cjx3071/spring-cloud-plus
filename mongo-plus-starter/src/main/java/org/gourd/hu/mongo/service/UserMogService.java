package org.gourd.hu.mongo.service;

import org.gourd.hu.mongo.model.entity.UserMog;

/**
 * @Description mongo用户服务类
 * @Author gourd.hu
 * @Date 2020/7/22 17:24
 * @Version 1.0
 */
public interface UserMogService {

    /**
     * 保存用户
     * @param userMog
     * @return
     */
    void save(UserMog userMog);
}

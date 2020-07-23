package org.gourd.hu.mongo.repository;

import org.gourd.hu.mongo.model.entity.UserMog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Description 用户接口
 * @Author gourd.hu
 * @Date 2020/7/22 16:04
 * @Version 1.0
 */
public interface UserRepository extends MongoRepository<UserMog,Long> {

    /**
     * 根据account和年龄范围查找
     *
     * @param account
     * @param ageS
     * @param ageE
     * @return
     */
    List<UserMog> findByAccountLikeAndAgeBetween(String account,Integer ageS,Integer ageE);

    /**
     * 根据年龄列表查询
     *
     * @param ages
     * @return
     */
    List<UserMog> findByAgeIn(List<Integer> ages);


}

package org.gourd.hu.mongo.repository;

import org.gourd.hu.mongo.model.entity.DeptMog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description 部门接口
 * @Author gourd.hu
 * @Date 2020/7/22 16:04
 * @Version 1.0
 */
public interface DeptRepository extends MongoRepository<DeptMog,Long> {



}

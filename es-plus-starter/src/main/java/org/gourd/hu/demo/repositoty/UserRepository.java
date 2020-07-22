package org.gourd.hu.demo.repositoty;

import org.gourd.hu.demo.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author gourd
 */
public interface UserRepository extends ElasticsearchRepository<UserEs,Long> {


}

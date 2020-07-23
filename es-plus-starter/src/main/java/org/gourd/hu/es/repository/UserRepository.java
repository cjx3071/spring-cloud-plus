package org.gourd.hu.es.repository;

import org.gourd.hu.es.model.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author gourd
 */
public interface UserRepository extends ElasticsearchRepository<UserEs,Long> {


}

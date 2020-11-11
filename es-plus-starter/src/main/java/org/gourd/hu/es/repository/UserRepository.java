package org.gourd.hu.es.repository;

import org.gourd.hu.es.model.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author gourd
 */
@Component
public interface UserRepository extends ElasticsearchRepository<UserEs,Long> {


}

package org.gourd.hu.es.repositoty;

import org.gourd.hu.es.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author gourd
 */
public interface UserRepository extends ElasticsearchRepository<UserEs,Long> {


}

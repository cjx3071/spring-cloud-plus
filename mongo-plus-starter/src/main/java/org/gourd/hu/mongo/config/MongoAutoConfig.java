package org.gourd.hu.mongo.config;

import org.gourd.hu.mongo.service.impl.UserMogServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author gourd.hu
 */
@Configuration
@Import({UserMogServiceImpl.class})
public class MongoAutoConfig {

    /**
     * 副本集模式支持
     * @param factory
     * @return
     */
    /*@Bean
    MongoTransactionManager transactionManager(MongoDbFactory factory){
        return new MongoTransactionManager(factory);
    }*/

}
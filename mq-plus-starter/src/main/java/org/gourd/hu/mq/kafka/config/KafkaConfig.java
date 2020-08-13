package org.gourd.hu.mq.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.gourd.hu.mq.kafka.listeners.KafkaConsumerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * kafka消息队列配置
 * @author gour.hu
 */
@EnableKafka
@Configuration
@ConditionalOnProperty(prefix = "spring.kafka",value = "bootstrap-servers")
@Import({KafkaConsumerListener.class})
public class KafkaConfig {

    /**
     * 创建Topic并设置分区数为8以及副本数为1
     *
     * @param topicName
     * @return
     */
    @Bean
    public NewTopic initialTopic(@Value("${spring.kafka.producer.gourd-topic:gourd-topic}")String topicName) {
        return new NewTopic(topicName,8, (short) 1 );
    }

}
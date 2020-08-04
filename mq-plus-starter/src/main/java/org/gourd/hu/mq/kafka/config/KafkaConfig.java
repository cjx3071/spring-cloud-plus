package org.gourd.hu.mq.kafka.config;

import org.gourd.hu.mq.kafka.listeners.KafkaConsumerListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * kafka消息队列配置
 * @author gour.hu
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.kafka",value = "bootstrap-servers")
@Import({KafkaConsumerListener.class})
public class KafkaConfig {

}
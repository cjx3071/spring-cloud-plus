package org.gourd.hu.mq.kafka.config;

import org.gourd.hu.mq.kafka.listeners.KafkaConsumerListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * active消息队列配置
 * @author gour.hu
 */
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Import({KafkaConsumerListener.class})
public class KafkaConfig {

}
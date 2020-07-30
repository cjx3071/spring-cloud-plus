package org.gourd.hu.mq.kafka.listeners;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * kafka 消费类
 * @author gourd
 */
@Slf4j
public class KafkaConsumerListener {


    @KafkaListener(topics = {"${spring.kafka.consumer.gourd-topic}"}, groupId = "${spring.kafka.consumer.group-id}" ,containerFactory = "kafkaListenerContainerFactory")
    public void kafkaConsumerTest(ConsumerRecord<String, Object> record) {
        log.info("消消费消息 topic = {} , content = {}",record.topic(),record.value());
        Object messageText = record.value();

    }


}

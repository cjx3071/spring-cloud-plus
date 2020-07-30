package org.gourd.hu.mq.activemq.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.holder.SpringContextHolder;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Description 发送消息工具类
 * @Author gourd.hu
 * @Date 2020/7/9 11:35
 * @Version 1.0
 */
@Slf4j
public class ActiveMqUtil {

    /**
     * 发送队列消息
     * @param queue
     * @param object
     */
    public static void sendQueueMessage(Queue queue,Object object){
        log.info("发送队列消息体：{}",object);
        String message = JSON.toJSONString(object);
        JmsMessagingTemplate jmsMessagingTemplate = SpringContextHolder.getBean(JmsMessagingTemplate.class);
        try {
            jmsMessagingTemplate.convertAndSend(queue, message);
        } catch (Exception e) {
            log.error("消息发送失败：{}",e);
        }

    }

    /**
     * 发送topic消息
     * @param topic
     * @param object
     */
    public static void sendTopicMessage(Topic topic,Object object){
        log.info("发送topic消息体：{}",object);
        String message = JSON.toJSONString(object);
        JmsMessagingTemplate jmsMessagingTemplate = SpringContextHolder.getBean(JmsMessagingTemplate.class);
        try {
            jmsMessagingTemplate.convertAndSend(topic, message);
        } catch (Exception e) {
            log.error("消息发送失败：{}",e);
        }

    }
}

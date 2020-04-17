package org.gourd.hu.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 消息消费
 *
 * @author admin
 */
@Component
@Slf4j
public class ConsumerService {


    /**
     * 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
     * @param message
     * @return
     */
    @JmsListener(destination = "gourd-queue",containerFactory = "jmsListenerContainerQueue")
    @SendTo("SQueue")
    public void handleMessage(final TextMessage message) throws JMSException {
        log.info("queue-consumer收到的报文为:" + message.getText());
    }

    @JmsListener(destination = "gourd-topic",containerFactory = "jmsListenerContainerTopic")
    @SendTo("STopic")// 加上此注解可以有返回值，否则只能为void
    public void receiveTopic(final TextMessage message) throws JMSException {
        log.info("topic-consumer收到的报文为:" + message.getText());
//        if(true){
//            throw new BusinessException("测试消息重试");
//        }
    }
 
}
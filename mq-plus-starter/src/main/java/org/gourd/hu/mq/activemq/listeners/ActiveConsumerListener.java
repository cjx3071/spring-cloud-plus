package org.gourd.hu.mq.activemq.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * activeMq消费类
 *
 * @author gourd.hu
 */
@Slf4j
public class ActiveConsumerListener {

    private static final String ARRAY_SEGMENT ="[";

    /**
     * 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
     * @param message
     * @return
     */
    @JmsListener(destination = "${spring.activemq.queue-name:gourd-queue}",containerFactory = "jmsListenerContainerQueue")
    @SendTo("gourd-queue")
    public void handleMessage(final TextMessage message) throws JMSException {
        String messageText = message.getText();
        log.info("queue-consumer收到的报文为:{}",messageText);
        if(messageText.contains(ARRAY_SEGMENT)){
            JSONArray jsonArray = JSON.parseArray(messageText);
        }else {
            JSONObject jsonObject = JSON.parseObject(messageText);
        }
    }

    @JmsListener(destination = "${spring.activemq.topic-name:gourd-topic}",selector="${spring.activemq.group-name:gourd-group}",containerFactory = "jmsListenerContainerTopic")
    @SendTo("gourd-topic")
    public void receiveTopic(final TextMessage message) throws JMSException {
        String messageText = message.getText();
        log.info("topic-consumer收到的报文为:{}",messageText);
        if(messageText.contains(ARRAY_SEGMENT)){
            JSONArray jsonArray = JSON.parseArray(messageText);
        }else {
            JSONObject jsonObject = JSON.parseObject(messageText);
        }
//        if(true){
//            throw new BusinessException("测试消息重试");
//        }
    }
 
}
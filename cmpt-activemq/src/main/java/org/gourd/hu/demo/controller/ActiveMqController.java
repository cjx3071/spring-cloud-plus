package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import java.io.Serializable;

/**
 * 队列消息控制器
 * @author gourd.hu
 */
@RestController
@RequestMapping("/activeMq")
@Api(tags = "消息测试API", description = "队列消息控制器" )
@Slf4j
public class ActiveMqController {


    @Autowired
    private Queue queue;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @GetMapping("send")
    @ApiOperation(value = "发送消息到队列")
    public BaseResponse send(@RequestParam("msg") String msg) {
        try {
            this.sendMessage(queue,msg);
        } catch (JMSException e) {
            log.error(e.getMessage(),e);
        }
        return BaseResponse.ok("success!");
    }

    @GetMapping("/topic")
    @ApiOperation(value = "发送消息到主题")
    public BaseResponse handlerActiveMq(@RequestParam("msg")String  msg) {
        try {
            this.sendMessage(topic,msg);
        } catch (JMSException e) {
            log.error(e.getMessage(),e);
        }
        return BaseResponse.ok("success!");
    }

    /**
     * 发送消息，destination是发送到的队列，message是待发送的消息
     *
     * @param destination
     * @param obj
     */
    public void sendMessage(Destination destination, Object obj) throws JMSException {
        ActiveMQObjectMessage activeMQObjectMessage = new ActiveMQObjectMessage();
        activeMQObjectMessage.setObject((Serializable) obj);
        jmsMessagingTemplate.convertAndSend(destination, activeMQObjectMessage);
    }
 
}

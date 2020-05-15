package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 队列消息控制器
 * @author gourd.hu
 */
@RestController
@RequestMapping("/activeMq")
@Api(tags = "消息测试API", description = "队列消息控制器" )
public class ActiveMqController {

    /**
     * 注入存放消息的队列，用于下列方法一
     */
    @Autowired
    private Queue queue;

    /**
     * 注入springboot封装的工具类
     */
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @GetMapping("send")
    @ApiOperation(value = "发送消息到队列")
    public BaseResponse send(@RequestParam("msg") String msg) {

        jmsMessagingTemplate.convertAndSend(queue, msg);
        return BaseResponse.ok("success!");
    }


    @Autowired
    private Topic topic;
 
    @GetMapping("/topic")
    @ApiOperation(value = "发送消息到主题")
    public BaseResponse handlerActiveMq(@RequestParam("msg")String  msg) {
        jmsMessagingTemplate.convertAndSend(topic, msg);
        return BaseResponse.ok("success!");
    }
 
}

package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.mq.activemq.config.ActiveMqConfig;
import org.gourd.hu.mq.activemq.utils.ActiveMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@RequestMapping("/mq/activeMq")
@Api(tags = "activeMq消息测试API" )
@Slf4j
@ConditionalOnBean({ActiveMqConfig.class})
public class ActiveMqController {


    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @GetMapping("send")
    @ApiOperation(value = "发送消息到队列")
    public BaseResponse send(@RequestParam("msg") String msg) {
        ActiveMqUtil.sendQueueMessage(queue,msg);
        return BaseResponse.ok("success!");
    }

    @GetMapping("/topic")
    @ApiOperation(value = "发送消息到主题")
    public BaseResponse handlerActiveMq(@RequestParam("msg")String  msg) {
        ActiveMqUtil.sendTopicMessage(topic,msg);
        return BaseResponse.ok("success!");
    }

 
}

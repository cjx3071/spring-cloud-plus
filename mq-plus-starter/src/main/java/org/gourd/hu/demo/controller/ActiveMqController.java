package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.mq.activemq.utils.MqUtil;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class ActiveMqController {


    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @GetMapping("send")
    @ApiOperation(value = "发送消息到队列")
    public BaseResponse send(@RequestParam("msg") String msg) {
        MqUtil.sendQueueMessage(queue,msg);
        return BaseResponse.ok("success!");
    }

    @GetMapping("/topic")
    @ApiOperation(value = "发送消息到主题")
    public BaseResponse handlerActiveMq(@RequestParam("msg")String  msg) {
        MqUtil.sendTopicMessage(topic,msg);
        return BaseResponse.ok("success!");
    }

 
}

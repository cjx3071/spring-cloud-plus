package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.mq.kafka.config.KafkaConfig;
import org.gourd.hu.mq.kafka.utils.KafkaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 队列消息控制器
 * @author gourd.hu
 */
@Slf4j
@RestController
@RequestMapping("/mq/kafka")
@Api(tags = "kafka测试API")
@ConditionalOnBean({KafkaConfig.class})
public class KafkaController {

    @Value("${spring.kafka.producer.gourd-topic}")
    private String topic;

    @GetMapping("/sendMsg")
    @ApiOperation(value = "发送消息到主题")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse sendMsg(@RequestParam("msg")String  msg) {
        KafkaUtil.sendTopicMessage(topic,msg);
        return BaseResponse.ok("success!");
    }
}

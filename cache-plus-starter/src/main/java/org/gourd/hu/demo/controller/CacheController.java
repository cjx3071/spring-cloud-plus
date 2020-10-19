package org.gourd.hu.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.cache.annotation.NoRepeatSubmit;
import org.gourd.hu.cache.utils.OrderNumberUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存相关
 * @author gourd.hu
 */
@RestController
@RequestMapping("/cache")
@Api(tags = "缓存相关操作")
@Slf4j
public class CacheController {

    @GetMapping("/orderNumber")
    @ApiOperation(value = "订单号")
    public BaseResponse getOrderNumber() {
        return BaseResponse.ok(OrderNumberUtil.getOrderNumber());
    }

    @GetMapping("/repeat")
    @ApiOperation(value = "测试重复调用")
    @NoRepeatSubmit(2)
    public BaseResponse repeatTest(@RequestParam String param) {
        return BaseResponse.ok("success,参数："+param);
    }

}
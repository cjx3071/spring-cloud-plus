package org.gourd.hu.sub.api;

import org.gourd.hu.sub.fallback.SubFallbackFactory;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * sub服务API调用类
 * contextId属性： 解决相同name不同client不同配置的问题
 * feign不支持下划线"_"，支持"-",否则报错：Service id not legal hostname
 * @author gourd
 */
@FeignClient(name = "sub",contextId = "sub-one" ,path = "/test",fallbackFactory = SubFallbackFactory.class)
public interface SubApi {


    @GetMapping("/hello/{id}")
    BaseResponse helloTest(@PathVariable("id") Long id);

    @GetMapping("/hello")
    BaseResponse helloTestParam(@RequestParam("id") Long id);

    @PostMapping("/hello")
    BaseResponse helloTestP(@RequestBody String name);

    @PostMapping("/seata-tx")
    BaseResponse seataAtTest();

    @PostMapping("/seata-tcc")
    BaseResponse seataTccTest(@RequestBody Map params);

}
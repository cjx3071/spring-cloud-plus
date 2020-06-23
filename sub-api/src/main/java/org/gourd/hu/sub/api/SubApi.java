package org.gourd.hu.sub.api;

import org.gourd.hu.sub.fallback.SubFallbackFactory;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * sub服务API调用类
 * @author gourd
 */
@FeignClient(name = "sub",path = "/test",fallbackFactory = SubFallbackFactory.class)
public interface SubApi {


    @GetMapping("/hello/{id}")
    BaseResponse helloTest(@PathVariable("id") Long id);

    @GetMapping("/hello")
    BaseResponse helloTestParam(@RequestParam("id") Long id);

    @PostMapping("/hello")
    BaseResponse helloTestP(@RequestBody String name);

    @PostMapping("/seata-tx")
    BaseResponse seataTxTest();

}
package org.gourd.hu.sub.controller;

import org.gourd.hu.sub.response.BaseResponse;
import org.gourd.hu.sub.service.CloudTestService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * spring cloud  测试提供者
 * @author gourd
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class CloudTestController {

    @GetMapping("/hello/{id}")
    public BaseResponse helloTest(@PathVariable Long id) {
        log.info("连接成功:"+ id );
        return BaseResponse.ok("sub服务，success!");
    }

    @GetMapping("/hello")
    public BaseResponse helloTestParam(@RequestParam Long id) {
        log.info("连接成功:"+ id );
        return BaseResponse.ok("sub服务，success!");
    }

    @PostMapping("/hello")
    public BaseResponse helloTestP(String name) {
        log.info("连接成功:"+ name);
        return BaseResponse.ok("sub服务post方法，success!");
    }

    @Autowired
    private CloudTestService cloudTestService;

    /**
     * 分布式事务测试
     * @return
     */
    @PostMapping("/seata-tx")
    public void seataTxTest(){
        log.info("sub Service ... xid: " + RootContext.getXID());
        cloudTestService.testSeata();
    }
}

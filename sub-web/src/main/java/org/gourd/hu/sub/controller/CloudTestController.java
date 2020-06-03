package org.gourd.hu.sub.controller;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.sub.api.SubApi;
import org.gourd.hu.sub.response.BaseResponse;
import org.gourd.hu.sub.service.CloudTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring cloud  测试提供者
 * @author gourd
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class CloudTestController implements SubApi {

    @Override
    public BaseResponse helloTest(@PathVariable Long id) {
        log.info("连接成功:"+ id );
        return BaseResponse.ok("sub服务，success!");
    }

    @Override
    public BaseResponse helloTestParam(@RequestParam Long id) {
        log.info("连接成功:"+ id );
        return BaseResponse.ok("sub服务，success!");
    }

    @Override
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
    @Override
    public BaseResponse seataTxTest(){
        log.info("sub Service ... xid: " + RootContext.getXID());
        cloudTestService.testSeata();
        return BaseResponse.ok("分布式事务测试方法，success!");
    }
}

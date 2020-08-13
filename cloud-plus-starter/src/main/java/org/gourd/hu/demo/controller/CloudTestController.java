package org.gourd.hu.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.sub.api.SubApi;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * 测试
 * @author gourd.hu
 */
@Api(tags = "分布式测试API", description = "分布式测试API" )
@RestController
@RequestMapping("/cloud")
@RefreshScope
@Slf4j
public class CloudTestController {

    @Value("${test.nacos.value:test}")
    private String nacosValue;

    @Autowired
    private SubApi subApi;

    @GetMapping("/nacos")
    @ApiOperation(value = "测试nacos配置热更新")
    public BaseResponse nacosTest() {
        log.info("nacosValue:"+ nacosValue);
        return BaseResponse.ok(nacosValue);
    }

    @GetMapping("/hello/{id}")
    @ApiOperation(value = "测试get路径传参")
    public BaseResponse helloTest(@PathVariable("id") Long id) {
        return subApi.helloTest(id);
    }

    @GetMapping("/hello")
    @SentinelResource(value="resource")
    @ApiOperation(value = "测试get传参")
    public BaseResponse helloTestParam(@RequestParam("id") Long id) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            Thread.interrupted();
//            log.error(e.getMessage(),e);
//        }
        return subApi.helloTestParam(id);
    }

    @PostMapping("/hello")
    @SentinelResource(value = "resource")
    @ApiOperation(value = "测试post传参")
    public BaseResponse helloTest(Long id,String name) {
        return subApi.helloTestP(name);
    }

//    ========================测试分布式事务=======================

    /**
     * 分布式事务测试
     * @return
     */
//    @PostMapping("/seata-tx")
//    @ApiOperation(value = "分布式事务测试")
//    @GlobalTransactional(name = "hu")
//    public void seataTxTest(){
//        log.info("hu Service ... xid: " + RootContext.getXID());
//        cloudTestService.testSeata();
//    }

    @GetMapping("/sentinel")
    @SentinelResource(value="/test/sentinel")
    @ApiOperation(value = "测试sentinel限流、熔断、系统防护")
    public BaseResponse sentinelTest() {
        try {
            // 模拟超时
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return BaseResponse.ok("success!");
    }

    @GetMapping("/sentinel-hot")
    @SentinelResource(value="hot-resource")
    @ApiOperation(value = "测试sentinel热点")
    public BaseResponse sentinelHot(String hotkey) {
        return BaseResponse.ok("success! hotkey: "+hotkey);
    }
}

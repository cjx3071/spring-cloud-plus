package org.gourd.hu.demo.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.demo.service.CloudTestService;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private CloudTestService cloudTestService;

    @GetMapping("/nacos")
    @ApiOperation(value = "测试nacos配置热更新")
    public BaseResponse nacosTest() {
        log.info("nacosValue:"+ nacosValue);
        return BaseResponse.ok(nacosValue);
    }

    @GetMapping("/hello/{id}")
    @ApiOperation(value = "测试get路径传参")
    public BaseResponse helloTest(@PathVariable("id") Long id) {
        return cloudTestService.helloTest(id);
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
        return cloudTestService.helloTestParam(id);
    }

    @PostMapping("/hello")
    @SentinelResource(value = "resource")
    @ApiOperation(value = "测试post传参")
    public BaseResponse helloTest(String name) {
        return cloudTestService.helloTestP(name);
    }

//    ========================测试分布式事务=======================

    /**
     * 分布式事务测试
     * @return
     */
    @PostMapping("/seata-ax")
    @ApiOperation(value = "分布式事务AT测试")
    @GlobalTransactional(name = "hu")
    public BaseResponse seataTxTest(){
        log.info("hu Service ... xid: " + RootContext.getXID());
        cloudTestService.seataAtTest();
        return BaseResponse.ok("分布式事务AT测试成功");
    }

    @GetMapping("/sentinel")
    @SentinelResource(value="/test/sentinel")
    @ApiOperation(value = "测试sentinel限流、熔断、系统防护")
    public BaseResponse sentinelTest() {
        int i = RandomUtil.getRandom().nextInt(0,2);
        log.info("随机数：{}",i);
        if(i ==1){
            throw new RuntimeException("模拟异常");
        }
        return BaseResponse.ok("success!");
    }

    @GetMapping("/sentinel-hot")
    @SentinelResource(value="hot-resource")
    @ApiOperation(value = "测试sentinel热点")
    public BaseResponse sentinelHot(String hotkey) {
        return BaseResponse.ok("success! hotkey: "+hotkey);
    }


    @PostMapping("/seata-tcc")
    @ApiOperation(value = "分布式事务TCC测试")
    @GlobalTransactional(name = "hu-tcc")
    public BaseResponse seataTccTest(@RequestBody Map params){
        log.info("hu Service ... xid: " + RootContext.getXID());
        cloudTestService.testSeataTccPrepare(params);
        return BaseResponse.ok("分布式事务TCC测试成功");
    }
}

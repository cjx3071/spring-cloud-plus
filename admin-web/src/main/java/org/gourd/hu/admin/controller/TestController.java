package org.gourd.hu.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.admin.service.TestService;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.cache.annotation.NoRepeatSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author gourd.hu
 */
@Api(tags = "项目测试API", description = "项目测试API" )
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/repeat")
    @ApiOperation(value = "测试重复调用")
    @NoRepeatSubmit(2)
    public BaseResponse repeatTest(@RequestParam String param) {
        return BaseResponse.ok("success,参数："+param);
    }

    @GetMapping("/log")
    @ApiOperation(value = "测试操作日志")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = false, dataType = "string", paramType = "header")})
    public BaseResponse logTest(@RequestParam String param) {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return BaseResponse.ok("success,参数："+param);
    }

    @GetMapping("/cache")
    @ApiOperation(value = "测试cache")
    public BaseResponse cacheTest(@RequestParam(required = false) String param1,@RequestParam(required = false) String param2) {
        return testService.testCache(param1,param2);
    }

    @GetMapping("/retry")
    @ApiOperation(value = "测试重试")
    public BaseResponse retryTest() {
        return testService.testRetry();
    }

    @GetMapping("/log-level")
    @ApiOperation(value = "测试操作日志级别")
    public BaseResponse logLevelTest() {
        log.debug("debug日志");
        log.info("info日志");
        return BaseResponse.ok("success");
    }
}

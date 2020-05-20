package org.gourd.hu.openapi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.log.annotation.OperateLog;
import org.gourd.hu.openapi.constant.AuthConstant;
import org.gourd.hu.openapi.dao.SysSecretDao;
import org.gourd.hu.openapi.dto.TestDTO;
import org.gourd.hu.openapi.entity.SysSecret;
import org.gourd.hu.openapi.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 * @author gourd.hu
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @Autowired
    private SysSecretDao sysSecretDao;

    @GetMapping("getT")
    @OperateLog("测试get请求")
    public BaseResponse testGet(@RequestParam String name){
        return BaseResponse.ok("Success");
    }

    @PostMapping("postT")
    @OperateLog("测试Post请求")
    public BaseResponse testPost(String name,String id){
        return BaseResponse.ok("Success");
    }

    @PostMapping("postT2")
    @OperateLog("测试Post请求")
    public BaseResponse testPost2(@RequestBody TestDTO testDTO){
        return BaseResponse.ok("Success");
    }



    @PostMapping("getSign")
    @OperateLog("生成签名")
    public String getSign(){
        Map<String, String> sParaTemp = new HashMap<>(16);
        sParaTemp.put(AuthConstant.NONCE_KEY, RandomUtils.nextLong()+"");
        sParaTemp.put(AuthConstant.ACCESS_KEY,"cloud-plus-key");
        sParaTemp.put(AuthConstant.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        log.info("生成签名的参数: ",JSON.toJSONString(sParaTemp));
        // 获取数据库密钥信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("app_key","cloud-plus-key");
        SysSecret sysSecret = sysSecretDao.selectOne(queryWrapper);
        // 断言appkey存在
        ResponseEnum.APP_KEY_ERROR.assertNotNull(sysSecret);
        sParaTemp.put(AuthConstant.SECRET_KEY, sysSecret.getSecretKey());
        String signStr = SignUtil.generateSign(sParaTemp,sysSecret.getExpireTimes());
        return signStr;
    }

}

package org.gourd.hu.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.admin.service.TestService;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@Description 测试service
 *@author gourd.hu
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {


    @Override
    @Cacheable("testCache")
    public BaseResponse testCache(String param1, String param2) {
        log.info("缓存未命中");
        return BaseResponse.ok("success!");
    }

    @Override
    public BaseResponse testRetry(){
        log.info("重试");
        int i  = 1/0;
        return BaseResponse.ok("success!");
    }

}

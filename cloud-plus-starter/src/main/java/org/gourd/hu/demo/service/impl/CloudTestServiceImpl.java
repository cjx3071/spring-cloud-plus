package org.gourd.hu.demo.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.BusinessException;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.demo.service.CloudTestService;
import org.gourd.hu.sub.api.SubApi;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class CloudTestServiceImpl implements CloudTestService {

    @Autowired
    private SubApi subApi;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void testSeata(){
        jdbcTemplate.update(
                "update rbac_depart set name = ? where id = ?",new Object[] { "seata", 1 });
        log.info("同步调用");
        BaseResponse subRes = subApi.seataTxTest();
        log.info("response:"+ JSON.toJSONString(subRes));
        // 模拟异常，看子事务是否回滚
        throw new BusinessException(ResponseEnum.INTERNAL_SERVER_ERROR);
    }
}

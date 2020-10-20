package org.gourd.hu.demo.service.impl;

import com.alibaba.fastjson.JSON;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

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
    public BaseResponse helloTest(Long id) {
        return subApi.helloTest(id);
    }

    @Override
    public BaseResponse helloTestParam(Long id) {
        return subApi.helloTestParam(id);
    }

    @Override
    public BaseResponse helloTestP(String name) {
        return subApi.helloTestP(name);
    }

    @Override
    public void seataAtTest(){
        jdbcTemplate.update(
                "update rbac_org set name = ? where id = ?", "seata", 1 );
        log.info("同步调用");
        BaseResponse subRes = subApi.seataAtTest();
        log.info("response:"+ JSON.toJSONString(subRes));
        // 模拟异常，看子事务是否回滚
        throw new BusinessException(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Boolean testSeataTccPrepare(@RequestBody Map params) {
        log.info("一阶段准备:{}", RootContext.getXID());
        subApi.seataTccTest(params);
        return Boolean.TRUE;
    }

    @Override
    public Boolean testTccCommit(BusinessActionContext context) {
        log.info("二阶段提交:{}",context.getXid());
        return Boolean.TRUE;
    }

    @Override
    public Boolean testTccRollback(BusinessActionContext context) {
        log.info("二阶段回滚:{}",context.getXid());
        return Boolean.TRUE;
    }
}

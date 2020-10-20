package org.gourd.hu.sub.service.impl;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.sub.service.CloudTestService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 *@Description 测试service
 *@Author gourd
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CloudTestServiceImpl implements CloudTestService {
    private final JdbcTemplate jdbcTemplate;


    public CloudTestServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void testSeata(){
        jdbcTemplate.update(
                "update sys_tenant set name = ? where id = ?","seata1", 2 );
    }

    @Override
    public Boolean testSeataTccPrepare(@RequestBody Map params){
        log.info("一阶段准备:{}",RootContext.getXID());
        jdbcTemplate.update(
                "update sys_tenant set name = ? where id = ?", "seata1", 2 );
        if(true){
            throw new RuntimeException("模拟异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean testTccCommit(BusinessActionContext context){
        log.info("二阶段提交:{}",context.getXid());
        return Boolean.TRUE;
    }

    @Override
    public Boolean testTccRollback(BusinessActionContext context){
        log.info("二阶段回滚:{}", context.getXid());
        return true;
    }
}

package org.gourd.hu.demo.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.gourd.hu.sub.response.BaseResponse;

import java.util.Map;

/**
 *@Description 测试service
 *@author gourd.hu
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
@LocalTCC
public interface CloudTestService {

    /**
     * 测试get路径传参
     * @param id
     * @return
     */
    BaseResponse helloTest(Long id);

    /**
     * 测试get传参
     * @param id
     * @return
     */
    BaseResponse helloTestParam(Long id);


    /**
     * 测试post传参
     * @param name
     * @return
     */
    BaseResponse helloTestP(String name);

    /**
     * 测试分布式事务
     */
    void seataAtTest();


    /**
     * 测试分布式事务
     */
    @TwoPhaseBusinessAction(name="testSeataTcc",commitMethod = "testTccCommit",rollbackMethod = "testTccRollback")
    Boolean testSeataTccPrepare(@BusinessActionContextParameter(paramName = "params") Map params);

    /**
     * 测试分布式事务提交
     */
    Boolean testTccCommit(BusinessActionContext context);

    /**
     * 测试分布式事务回滚
     */
    Boolean testTccRollback(BusinessActionContext context);
}

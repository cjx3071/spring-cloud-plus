package org.gourd.hu.sub.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.Map;

/**
 *@Description 测试service
 *@Author gourd
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
@LocalTCC
public interface CloudTestService{


    void testSeata();

    @TwoPhaseBusinessAction(name="testTccAction",commitMethod = "testTccCommit",rollbackMethod = "testTccRollback")
    Boolean testSeataTccPrepare(@BusinessActionContextParameter(paramName = "params") Map params);

    Boolean testTccCommit(BusinessActionContext context);

    Boolean testTccRollback(BusinessActionContext context);
}

package org.gourd.hu.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;

/**
 * @author gourd.hu
 */
@Slf4j
public class SentinelExceptionHandler {

    public static BaseResponse handleException(BlockException ex) {
        log.error(ex.getMessage(),ex);
        if(ex instanceof FlowException){
            return BaseResponse.fail("限流啦！");
        }else if(ex instanceof DegradeException){
            return BaseResponse.fail("熔断降级啦！");
        }else if(ex instanceof ParamFlowException){
            return BaseResponse.fail("热点限流啦！");
        }else if(ex instanceof SystemBlockException){
            return BaseResponse.fail("系统保护啦！");
        }else if(ex instanceof AuthorityException){
            return BaseResponse.fail(ResponseEnum.UNAUTHORIZED,"授权限制了！");
        }
        return BaseResponse.fail("未知异常！");
    }
}
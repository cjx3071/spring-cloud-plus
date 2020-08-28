package org.gourd.hu.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * sentinel相关异常捕获
 * @author gourd.hu
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SentinelExceptionHandler{

    @ExceptionHandler(value = UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static BaseResponse handleException(UndeclaredThrowableException ex) {
        log.error(ex.getMessage(),ex);
        Throwable undeclaredThrowable = ex.getUndeclaredThrowable();
        if(undeclaredThrowable instanceof FlowException){
            return BaseResponse.fail("限流啦！");
        }else if(undeclaredThrowable instanceof DegradeException){
            return BaseResponse.fail("熔断啦！");
        }else if(undeclaredThrowable instanceof ParamFlowException){
            return BaseResponse.fail("热点限流啦！");
        }else if(undeclaredThrowable instanceof SystemBlockException){
            return BaseResponse.fail("系统限流啦！");
        }else if(undeclaredThrowable instanceof AuthorityException){
            return BaseResponse.fail(ResponseEnum.UNAUTHORIZED,"授权限制了！");
        }
        return BaseResponse.fail("Sentinel系统保护！");
    }
}
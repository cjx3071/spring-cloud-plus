package org.gourd.hu.rbac.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * shiro权限异常处理器
 * @author gourd.hu
 */
@RestControllerAdvice
@Slf4j
public class ShiroExceptionHandler {
    /**
     * 捕捉所有Shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public BaseResponse handle401(ShiroException e) {
        log.error("异常信息：",e);
        return BaseResponse.fail(ResponseEnum.UNAUTHORIZED, "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public BaseResponse handle401(UnauthorizedException e) {
        log.error("异常信息：",e);
        return BaseResponse.fail(ResponseEnum.UNAUTHORIZED, "无权访问(Unauthorized):没有此请求所需权限(" + e.getMessage() + ")");
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public BaseResponse handle401(UnauthenticatedException e) {
        log.error("异常信息：",e);
        return BaseResponse.fail(ResponseEnum.UNAUTHORIZED, "无权访问(Unauthorized):匿名Subject，请先登录");
    }
}

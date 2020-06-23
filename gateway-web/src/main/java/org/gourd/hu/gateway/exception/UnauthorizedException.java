package org.gourd.hu.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @Description 未授权异常
 * @Author gourd.hu
 * @Date 2020/6/23 17:44
 * @Version 1.0
 */
public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED,message);
    }

}

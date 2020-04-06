package org.gourd.hu.base.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常
 *
 * @author gourd.hu
 * @date 2018-11-08
 */
public class BusinessException extends BaseException {

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessException(HttpStatus httpStatus) {
        super(httpStatus);
    }

}

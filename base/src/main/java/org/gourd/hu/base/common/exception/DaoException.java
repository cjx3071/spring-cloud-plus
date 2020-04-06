package org.gourd.hu.base.common.exception;

import org.springframework.http.HttpStatus;

/**
 * DAO异常
 *
 * @author gourd.hu
 * @date 2018-11-08
 */
public class DaoException extends BaseException {

	public DaoException(String message) {
        super(message);
    }

    public DaoException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public DaoException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}

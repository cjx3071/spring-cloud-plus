package org.gourd.hu.base.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * @author gourd
 * @date 2018-11-23
 * 自定义业务异常
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg, Throwable cause){
        super(msg,cause);
    }

    public BusinessException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
    public BusinessException(HttpStatus status, String msg, Throwable cause){
        super(msg,cause);
        this.status = status.value();
    }
}

package org.gourd.hu.base.data;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: gourd
 * @Description: 接口返回基础类
 * @Date:Created in 2018/8/27 16:19.
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private String token;
    private Date timestamp = DateUtil.date();
    public BaseResponse() {

    }
    public BaseResponse(int code, String msg, T data, String token) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }
    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public BaseResponse(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public static BaseResponse ok(Object o) {
        return new BaseResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),o);
    }
    public static BaseResponse ok(String msg) {
        return new BaseResponse(HttpStatus.OK.value(), msg);
    }

    public static BaseResponse ok(String msg,Object o) {
        return new BaseResponse(HttpStatus.OK.value(), msg,o);
    }
    public static BaseResponse failure(String msg) {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
    public static BaseResponse failure( int code,String msg) {
        return new BaseResponse(code,msg );
    }

}

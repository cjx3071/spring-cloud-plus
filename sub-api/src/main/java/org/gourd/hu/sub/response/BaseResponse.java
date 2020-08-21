package org.gourd.hu.sub.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * REST API 返回结果
 * </p>
 *
 * @author gourd.hu
 * @since 2018-11-08
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
	private static final long serialVersionUID = 8004487252556526569L;

	/**
     * 响应码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public BaseResponse() {
        time  = LocalDateTime.now();
    }

    public static BaseResponse<Boolean> result(boolean flag){
        if (flag){
            return ok();
        }
        return fail();
    }

    public static BaseResponse<Boolean> result(HttpStatus httpStatus){
        return result(httpStatus,null);
    }

    public static <T> BaseResponse<T> result(HttpStatus httpStatus, T data){
        return result(httpStatus,null,data);
    }

    public static <T> BaseResponse<T> result(HttpStatus httpStatus, String message, T data){
        boolean success = false;
        if (httpStatus.value() == HttpStatus.OK.value()){
            success = true;
        }
        if (StringUtils.isBlank(message)){
            String apiMessage = httpStatus.getReasonPhrase();
            message = apiMessage;
        }
        return (BaseResponse<T>) BaseResponse.builder()
                .code(httpStatus.value())
                .message(message)
                .data(data)
                .success(success)
                .time(LocalDateTime.now())
                .build();
    }

    public static BaseResponse<Boolean> ok(){
        return ok(null);
    }

    public static <T> BaseResponse<T> ok(T data){
        return result(HttpStatus.OK,data);
    }

    public static <T> BaseResponse<T> ok(String message,T data ){
        return result(HttpStatus.OK,message,data);
    }

    public static BaseResponse<Map<String,Object>> okMap(String key, Object value){
        Map<String,Object> map = new HashMap<>(1);
        map.put(key,value);
        return ok(map);
    }

    public static BaseResponse<Boolean> fail(HttpStatus httpStatus){
        return result(httpStatus,null);
    }

    public static BaseResponse<String> fail(String message){
        return result(HttpStatus.INTERNAL_SERVER_ERROR,message,null);

    }

    public static <T> BaseResponse<T> fail(HttpStatus httpStatus, T data){
        if (HttpStatus.OK == httpStatus){
            throw new RuntimeException("失败结果状态码不能为" + HttpStatus.OK.value());
        }
        return result(httpStatus,data);

    }

    public static BaseResponse<String> fail(Integer errorCode, String message){
        return new BaseResponse<String>()
                .setSuccess(false)
                .setCode(errorCode)
                .setMessage(message);
    }

    public static BaseResponse<Boolean> fail() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
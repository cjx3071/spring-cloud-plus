package org.gourd.hu.base.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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


    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应数据")
    private T data;

    @ApiModelProperty("错误信息集合")
    private List<String> errors;

    @ApiModelProperty("响应时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        return result(httpStatus,null,data,null);
    }

    public static <T> BaseResponse<T> result(HttpStatus httpStatus, String message, T data,List<String> errors){
        boolean success = false;
        if (httpStatus.value() == HttpStatus.OK.value()){
            success = true;
        }
        String apiMessage = httpStatus.getReasonPhrase();
        if (StringUtils.isBlank(message)){
            message = apiMessage;
        }
        return (BaseResponse<T>) BaseResponse.builder()
                .code(httpStatus.value())
                .message(message)
                .data(data)
                .success(success)
                .time(LocalDateTime.now())
                .errors(errors)
                .build();
    }

    public static BaseResponse<Boolean> ok(){
        return ok(null);
    }

    public static <T> BaseResponse<T> ok(T data){
        return result(HttpStatus.OK,data);
    }

    public static <T> BaseResponse<T> ok(String message,T data ){
        return result(HttpStatus.OK,message,data,null);
    }

    public static BaseResponse<Boolean> fail() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static BaseResponse<Boolean> fail(HttpStatus httpStatus){
        if (HttpStatus.OK == httpStatus){
            throw new RuntimeException("失败结果状态码不能为" + HttpStatus.OK.value());
        }
        return result(httpStatus,null);
    }

    public static BaseResponse<String> fail(String message){
        return result(HttpStatus.INTERNAL_SERVER_ERROR,message,null,null);

    }

    public static BaseResponse<String> fail(HttpStatus httpStatus, String message){
        if (HttpStatus.OK == httpStatus){
            throw new RuntimeException("失败结果状态码不能为" + HttpStatus.OK.value());
        }
        return result(httpStatus,message,null,null);

    }

    public static BaseResponse<String> fail(HttpStatus httpStatus, String message,List<String> errors){
        return result(httpStatus,message,null,errors);

    }

}
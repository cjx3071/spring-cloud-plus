package org.gourd.hu.es.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.gourd.hu.core.base.dto.BaseFindDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
public class UserEsFindDTO extends BaseFindDTO {

    /**
     * 年龄
     */
    private  Integer age;

    /**
     * 关键字
     */
    private  String keyword;
    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private  Date birth;

    /**
     * 账号
     */
    private  String account;


    /**
     * 邮箱
     */
    private  String email;

    /**
     * 手机
     */
    private  String mobilePhone;


}
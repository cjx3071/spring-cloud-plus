package org.gourd.hu.rbac.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class RbacUserCreateDTO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 承租人id
     */
    private Long tenantId;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Length(min = 1,max = 20,message = "账号长度不合法")
    @Pattern(regexp = "[A-Za-z0-9]+",message = "账号格式不正确,只能含有字母和数字")
    private  String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 1,max = 20,message = "账号长度不合法")
    private  String password;
    /**
     * 年龄
     */
    private  Integer age;

    /**
     * 姓名
     */
    private  String name;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private  Date birth;



    /**
     * 昵称
     */
    private  String nickName;

    /**
     * 邮箱
     */
    private  String email;

    /**
     * 手机
     */
    private  String mobilePhone;

    /**
     * 头像地址
     */
    private  String photoUrl;


}
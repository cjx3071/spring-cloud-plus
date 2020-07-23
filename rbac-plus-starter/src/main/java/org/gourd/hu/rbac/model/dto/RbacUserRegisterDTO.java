package org.gourd.hu.rbac.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.gourd.hu.core.constant.RegexConstant;
import org.gourd.hu.rbac.validation.annotations.Mobile;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
@ApiModel(value = "用户注册对象", description = "用户注册对象")
public class RbacUserRegisterDTO {

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank(message = "账号不能为空")
    @Length(min = 1,max = 20,message = "账号长度不合法")
    @Pattern(regexp = "[A-Za-z0-9]+@{1}[A-Za-z0-9]+",message = "账号格式不正确，例：gourd@1001")
    private  String account;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 1,max = 20,message = "账号长度不合法")
    private  String password;

    @ApiModelProperty("年龄")
    private  Integer age;

    @ApiModelProperty("姓名")
    private  String name;

    @ApiModelProperty("生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private  Date birth;

    @ApiModelProperty("昵称")
    private  String nickName;

    @ApiModelProperty("邮箱")
    @Email(regexp = RegexConstant.REGEX_EMAIL,message = "邮箱格式不正确")
    private  String email;

    @ApiModelProperty("手机")
    @Mobile
    private  String mobilePhone;

    @ApiModelProperty("头像地址")
    private  String photoUrl;


}
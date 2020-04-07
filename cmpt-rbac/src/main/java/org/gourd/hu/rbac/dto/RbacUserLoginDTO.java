package org.gourd.hu.rbac.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class RbacUserLoginDTO{


    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Length(min = 1, max = 20, message = "账号长度不合法")
    @Pattern(regexp = "[A-Za-z0-9]+@{1}[A-Za-z0-9]+",message = "账号格式不正确，例：gourd@1001")
    private  String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 1,max = 20,message = "账号长度不合法")
    private  String password;

}
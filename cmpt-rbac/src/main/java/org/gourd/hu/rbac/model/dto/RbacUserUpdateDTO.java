package org.gourd.hu.rbac.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
@ApiModel(value = "用户更新对象", description = "用户更新对象")
public class RbacUserUpdateDTO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("年龄")
    private  Integer age;

    @ApiModelProperty("姓名")
    private  String name;

    @ApiModelProperty("昵称")
    private  String nickName;

    @ApiModelProperty("邮箱")
    @Email
    private  String email;

    @ApiModelProperty("手机号")
    private  String mobilePhone;


}
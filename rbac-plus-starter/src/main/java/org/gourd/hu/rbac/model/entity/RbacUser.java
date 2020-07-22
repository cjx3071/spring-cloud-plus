package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;
import org.gourd.hu.core.enums.SexEnum;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_user")
@ApiModel(value = "RbacUser对象", description = "用户表")
public class RbacUser extends BaseEntity<RbacUser> {

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "拼音")
    private String pinYin;

    @ApiModelProperty(value = "性别（M-男，F-女，X-未知）")
    private SexEnum sex;

    @ApiModelProperty(value = "生日")
    private LocalDateTime birth;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String mobilePhone;

    @ApiModelProperty(value = "头像地址")
    private String photoUrl;

}

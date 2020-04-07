package org.gourd.hu.rbac.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gourd.hu.base.common.entity.BaseEntity;
import org.gourd.hu.core.enums.SexEnum;

import java.util.Date;
import java.util.Set;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("rbac_user")
public class RbacUser extends BaseEntity {

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    @TableField("pin_yin")
    private String pinYin;

    /**
     * 性别（M-男，F-女，X-未知）
     */
    @EnumValue
    private SexEnum sex;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 头像地址
     */
    private String photoUrl;

    /**
     * 角色和权限集合
     */
    @TableField(exist = false)
    private Set<String> authorities;


}
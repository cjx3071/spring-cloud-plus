package org.gourd.hu.rbac.dto;

import lombok.Data;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class RbacUserUpdateDTO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 年龄
     */
    private  Integer age;

    /**
     * 姓名
     */
    private  String name;


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


}
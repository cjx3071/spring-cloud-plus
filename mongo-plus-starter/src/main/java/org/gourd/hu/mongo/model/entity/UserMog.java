package org.gourd.hu.mongo.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * mongo用户对象
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
@Document("user")
public class UserMog {

    @Id
    private Long id;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    /**
     * 账号
     */
    private String account;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 头像地址
     */
    private String photoUrl;


    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 部门对象
     */
    @DBRef
    private DeptMog dept;


}
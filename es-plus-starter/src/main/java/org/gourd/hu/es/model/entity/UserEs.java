package org.gourd.hu.es.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
@Document(indexName = "gourd",type = "user", shards = 1, replicas = 0, refreshInterval ="-1")
public class UserEs {

    @Id
    private Long id;

    /**
     * 年龄
     */
    @Field(type = FieldType.Integer)
    private  Integer age;

    /**
     * 姓名
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private  String name;

    /**
     * 昵称
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private  String nickName;
    /**
     * 性别
     */
    @Field(type = FieldType.Keyword)
    private String sex;

    /**
     * 生日
     */
    @Field(type = FieldType.Date,format = DateFormat.year_month_day)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd",timezone="GMT+8" )
    private  Date birth;

    /**
     * 账号
     */
    @Field(type = FieldType.Keyword)
    private  String account;


    /**
     * 邮箱
     */
    @Field(type = FieldType.Keyword)
    private  String email;

    /**
     * 手机
     */
    @Field(type = FieldType.Keyword)
    private  String mobilePhone;

    /**
     * 头像地址
     */
    @Field(type = FieldType.Keyword)
    private  String photoUrl;


}
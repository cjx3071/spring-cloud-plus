package org.gourd.hu.mongo.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
public class UserMogTestFindDTO{

    /**
     * 年龄集合
     */
    private List<Integer> ages;

    /**
     * 账号
     */
    private  String account;

    /**
     * 开始年龄
     */
    private  Integer startAge;

    /**
     * 结束年龄
     */
    private  Integer endAge;

}
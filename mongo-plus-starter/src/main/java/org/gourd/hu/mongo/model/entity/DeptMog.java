package org.gourd.hu.mongo.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * mongo部门对象
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
@Document("dept")
public class DeptMog {

    @Id
    private Long id;

    /**
     * 姓名
     */
    private  String code;

    /**
     * 姓名
     */
    private  String name;

    /**
     * 备注
     */
    private String description;



}
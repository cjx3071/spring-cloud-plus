package org.gourd.hu.mongo.model.dto;

import lombok.Data;
import org.gourd.hu.core.base.dto.BaseFindDTO;

/**
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
public class UserMogFindDTO extends BaseFindDTO {

    /**
     * 年龄
     */
    private  Integer age;

    /**
     * 账号
     */
    private  String account;

    /**
     * 名称
     */
    private  String name;

    /**
     * 部门名
     */
    private  String deptName;

}
package org.gourd.hu.doc.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.gourd.hu.doc.excel.converter.Date2StrConverter;
import org.gourd.hu.doc.excel.converter.SexConverter;
import org.gourd.hu.doc.excel.enums.SexEnum;

import java.time.LocalDateTime;

/**
 * 用户导出导入实体
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Data
public class UserPO {

    /**
     * 年龄
     */
    @ExcelProperty(index = 1,value = {"基础信息","年龄"})
    private Integer age;

    /**
     * 姓名
     */
    @ExcelProperty(index = 0,value = {"基础信息","姓名"})
    private String name;

    /**
     * 性别（M-男，F-女，X-未知）
     */
    @ExcelProperty(index = 2,value = {"基础信息","性别"},converter = SexConverter.class)
    private SexEnum sex;

    /**
     * 生日
     */
    @ExcelProperty(index = 3,value = "生日",converter = Date2StrConverter.class)
    private LocalDateTime birth;

    /**
     * 邮箱
     */
    @ExcelProperty(index = 4,value = {"通讯信息","邮箱"})
    private String email;

    /**
     * 手机
     */
    @ExcelProperty(index = 5,value =  {"通讯信息","手机"})
    private String mobilePhone;

}
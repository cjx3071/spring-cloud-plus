package org.gourd.hu.doc.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 部门导出导入实体
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Data
public class DepartPO {

    /**
     * 姓名
     */
    @ExcelProperty(index = 0,value = {"名称"})
    private String name;

    /**
     * 编号
     */
    @ExcelProperty(index = 1,value = {"编号"})
    private String code;

    /**
     * 描述
     */
    @ExcelProperty(index = 2,value =  {"描述"})
    private String description;

}
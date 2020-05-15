package org.gourd.hu.core.base.dto;

import lombok.Data;
import org.gourd.hu.core.enums.SortEnum;


/**
 * 查询分页基础类
 * @author gourd
 */
@Data
public class BaseFindDTO {
    /**
     * 页数，默认值 1
     */
    private Integer pageNo =1;
    /**
     * 每页条数，默认值 10
     */
    private Integer pageSize =10;
    /**
     * 排序字段
     */
    private String orderColumn;

    /**
     * 排序类型
     */
    private SortEnum orderType;

}

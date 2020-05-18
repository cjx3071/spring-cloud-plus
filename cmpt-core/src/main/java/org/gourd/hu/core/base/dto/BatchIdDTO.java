package org.gourd.hu.core.base.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description 批量id请求
 * @Author gourd.hu
 * @Date 2020/5/18 10:39
 * @Version 1.0
 */
@Data
public class BatchIdDTO<T> {
    /**
     * id集合
     */
    private List<T> ids;
}

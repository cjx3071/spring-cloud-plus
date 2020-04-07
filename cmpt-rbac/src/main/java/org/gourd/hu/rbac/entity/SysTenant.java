package org.gourd.hu.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.base.common.entity.BaseEntity;

/**
 * <p>
 * 承租人表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {

    /**
     * 号码
     */
    private String number;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;



}

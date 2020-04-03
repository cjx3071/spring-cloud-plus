package org.gourd.hu.base.data;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共实体父类
 * @author gourd
 */
@Data
public class BaseEntity extends Model{

    /**
     * 主键id
     */
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    /**
     * 承租人id，加上exist = false，不然会和设置承租人拦截器冲突
     */
    @TableField(exist = false)
    private Long tenantId;

    /**
     * 创建人
     */
    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time",fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long version;

    /**
     * 逻辑删除状态
     */
    @TableLogic
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 冗余属性
     */
    private String attribute;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}

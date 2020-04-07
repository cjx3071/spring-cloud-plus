package org.gourd.hu.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gourd.hu.base.common.entity.BaseEntity;

/**
 * @author gourd
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("rbac_permission")
public class RbacPermission extends BaseEntity {

    /**
     * 代码
     */
    private  String code;

    /**
     * 名称
     */
    private  String name;

    /**
     * 描述
     */
    private  String description;

}
package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_role_permission")
@ApiModel(value = "RbacRolePermission对象", description = "角色权限关系表")
public class RbacRolePermission extends BaseEntity<RbacRolePermission> {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long permissionId;


}

package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

/**
 * <p>
 * 用户组角色关系表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_group_role")
@ApiModel(value = "RbacGroupRole对象", description = "用户组角色关系表")
public class RbacGroupRole extends BaseEntity<RbacGroupRole> {

    @ApiModelProperty(value = "用户组id")
    private Long groupId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

}

package org.gourd.hu.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rbac_user_group")
@ApiModel(value = "RbacUserGroup对象", description = "用户组表")
public class RbacUserGroup extends BaseEntity<RbacUserGroup> {


    @ApiModelProperty(value = "用户组名")
    private String name;

    @ApiModelProperty(value = "用户组代码")
    private String code;

    @ApiModelProperty(value = "父id")
    private Long parentId;

}

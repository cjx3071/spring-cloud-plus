package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.rbac.model.entity.RbacOrgRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 组织角色关系表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Repository
public interface RbacOrgRoleDao extends BaseMapper<RbacOrgRole> {

}

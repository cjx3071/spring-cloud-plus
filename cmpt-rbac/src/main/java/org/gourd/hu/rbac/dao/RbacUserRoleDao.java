package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.rbac.model.entity.RbacUserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacUserRoleDao extends BaseMapper<RbacUserRole> {

}

package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.gourd.hu.rbac.model.entity.RbacPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 权限
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacPermissionDao extends BaseMapper<RbacPermission> {

    List<RbacPermission> findByRoleIds(@Param("roleIds") Set<Long> roleIds);

}

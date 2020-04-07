package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.rbac.entity.RbacPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacPermissionDao extends BaseMapper<RbacPermission> {

    List<RbacPermission> findByRoleIds(@Param("roleIds") List<Long> roleIds);

}

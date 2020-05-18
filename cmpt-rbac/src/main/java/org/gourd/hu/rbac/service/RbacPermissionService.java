package org.gourd.hu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.rbac.model.entity.RbacPermission;

import java.util.List;
import java.util.Set;

/**
 * 权限
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
public interface RbacPermissionService extends IService<RbacPermission> {

    List<RbacPermission> findByRoleIds(Set<Long> roleIds);

}
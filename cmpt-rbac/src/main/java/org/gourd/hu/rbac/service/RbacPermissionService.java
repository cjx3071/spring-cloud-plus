package org.gourd.hu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.rbac.entity.RbacPermission;

import java.util.List;

/**
 * 权限
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
public interface RbacPermissionService extends IService<RbacPermission> {

    List<RbacPermission> findByRoleIds(List<Long> roleIds);

}
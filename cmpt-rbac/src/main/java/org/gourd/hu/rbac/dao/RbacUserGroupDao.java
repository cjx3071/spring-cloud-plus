package org.gourd.hu.rbac.dao;

import org.gourd.hu.rbac.entity.RbacUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Repository
public interface RbacUserGroupDao extends BaseMapper<RbacUserGroup> {

}

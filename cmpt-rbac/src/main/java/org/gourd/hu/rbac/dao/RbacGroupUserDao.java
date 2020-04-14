package org.gourd.hu.rbac.dao;

import org.gourd.hu.rbac.entity.RbacGroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户组用户关系表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Repository
public interface RbacGroupUserDao extends BaseMapper<RbacGroupUser> {

}

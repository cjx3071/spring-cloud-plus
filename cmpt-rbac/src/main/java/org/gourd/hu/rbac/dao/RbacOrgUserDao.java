package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.rbac.model.entity.RbacOrgUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户组织关系表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Repository
public interface RbacOrgUserDao extends BaseMapper<RbacOrgUser> {

}

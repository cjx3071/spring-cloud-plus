package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gourd.hu.rbac.model.entity.RbacOrg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 组织表 Mapper 接口
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Repository
public interface RbacOrgDao extends BaseMapper<RbacOrg> {
    /**
     * 获取组织的所有子集组织
     *
     * @param orgId
     * @return
     */
    @Select(value = "SELECT * FROM ( SELECT id, parent_id, id, code, name FROM rbac_org WHERE parent_id IS NOT NULL ) rd, " +
            " ( SELECT @pid := #{orgId} ) pd " +
            " WHERE ( FIND_IN_SET( parent_id, @pid ) > 0 AND @pid := concat( @pid, ',', dept_id ) )")
    List<Long> findAllChildrenById(@Param("orgId") String orgId);


    /**
     * 获取组织的所有子集组织
     *
     * @param path
     * @return
     */
    @Select(value = "SELECT id FROM rbac_org WHERE path LIKE #{path}%")
    List<Long> findAllChildrenByPath(@Param("path") String path);
}

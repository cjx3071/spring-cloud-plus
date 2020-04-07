package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gourd.hu.rbac.entity.RbacDepart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 * Created by CodeGen .
 */
@Repository
public interface RbacDepartDao extends BaseMapper<RbacDepart> {

    /**
     * 获取某个部门的所有子集部门
     *
     * @param departId
     * @return
     */
    @Select(value = "SELECT * FROM ( SELECT id, parent_id, dept_id, dept_code, dept_name FROM rbac_department WHERE parent_id IS NOT NULL ) rd, " +
            " ( SELECT @pid := #{departId} ) pd " +
            " WHERE ( FIND_IN_SET( parent_id, @pid ) > 0 AND @pid := concat( @pid, ',', dept_id ) )")
    List<Long> findAllChildrenById(@Param("departId") String departId);


    /**
     * 获取某个部门的所有子集部门
     *
     * @param path
     * @return
     */
    @Select(value = "SELECT id FROM rbac_depart WHERE path LIKE #{userCode}%")
    List<Long> findAllChildrenByPath(@Param("path") String path);

}

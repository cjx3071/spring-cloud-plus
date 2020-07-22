package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.gourd.hu.rbac.model.entity.RbacRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacRoleDao extends BaseMapper<RbacRole> {

    List<RbacRole> findByCodeIn(@Param("codes") List<String> codes);


    List<RbacRole> findByUserId(@Param("userId") Long userId);

}

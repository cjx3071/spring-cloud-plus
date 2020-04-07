package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gourd.hu.rbac.dto.RbacUserDeptSearchDTO;
import org.gourd.hu.rbac.entity.RbacUser;
import org.gourd.hu.rbac.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户操作dao
 *
 * @author gourd
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacUserDao extends BaseMapper<RbacUser> {

    RbacUser getByAccount(@Param("account") String account);

    RbacUser getByAccountAndTenantId(@Param("account") String account, @Param("tenantId") Long tenantId);


    IPage<UserVO> findUsersDept(Page page, @Param("userDept") RbacUserDeptSearchDTO userDeptSearchDTO, @Param("tenantId") Long tenantId);

}

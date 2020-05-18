package org.gourd.hu.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.gourd.hu.rbac.model.dto.RbacUserOrgSearchDTO;
import org.gourd.hu.rbac.model.entity.RbacUser;
import org.gourd.hu.rbac.model.vo.UserVO;
import org.springframework.stereotype.Repository;

/**
 * 用户操作dao
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Repository
public interface RbacUserDao extends BaseMapper<RbacUser> {

    RbacUser getByAccount(@Param("account") String account);

    RbacUser getByAccountAndTenantId(@Param("account") String account, @Param("tenantId") Long tenantId);


    IPage<UserVO> findUsersOrg(Page page, @Param("userOrg") RbacUserOrgSearchDTO userOrgSearchDTO, @Param("tenantId") Long tenantId);

}

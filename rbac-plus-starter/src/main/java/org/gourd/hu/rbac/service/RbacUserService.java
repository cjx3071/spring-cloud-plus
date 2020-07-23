package org.gourd.hu.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.model.dto.*;
import org.gourd.hu.rbac.model.entity.RbacUser;
import org.gourd.hu.rbac.model.vo.UserVO;

import java.util.List;


/**
 * 用户
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
public interface RbacUserService extends IService<RbacUser> {
    /**
     * 根据账号获取用户信息
     * @param account
     * @return
     */
    UserVO getByAccount(String account);

    /**
     * 根据账号和承租人id获取用户信息
     * @param account
     * @param tenantId
     * @return
     */
    RbacUser getByAccountAndTenantId(String account, Long tenantId);

    /**
     * 获取所有用户信息
     * @return
     */
    List<UserVO> findAll();

    /**
     * 根据条件获取用户
     * @return
     */
    IPage<UserVO> find(RbacUserSearchDTO rbacUserDTO, Page page);


    /**
     * 根据条件获取用户
     * @return
     */
    IPage<UserVO> findUsersOrg(RbacUserOrgSearchDTO rbacUserOrgSearchDTO, Page page);

    /**
     * 注册用户
     * @return
     */
    UserVO register(RbacUserRegisterDTO user);

    /**
     * 创建用户
     * @return
     */
    UserVO create(RbacUserOperateDTO user);

    /**
     * 更新用户
     * @return
     */
    int update(RbacUserOperateDTO user);

    /**
     * 删除用户
     * @return
     */
    int delete(Long userId);

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    UserVO getById(Long userId);

    /**
     * 获取当前用户信息
     * @return
     */
    JwtToken getCurrent();
}
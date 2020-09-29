package org.gourd.hu.rbac.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.rbac.auth.jwt.JwtClaim;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.model.dto.RbacUserRegisterDTO;
import org.gourd.hu.rbac.model.entity.RbacPermission;
import org.gourd.hu.rbac.model.entity.RbacRole;
import org.gourd.hu.rbac.model.entity.RbacUser;
import org.gourd.hu.rbac.model.entity.SysTenant;
import org.gourd.hu.rbac.model.vo.UserVO;
import org.gourd.hu.rbac.service.*;
import org.gourd.hu.rbac.utils.ShiroKitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 鉴权服务
 *
 * @author: gourd.hu
 * createAt: 2018/9/17
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RbacUserService rbacUserService;
    @Autowired
    private SysTenantService sysTenantService;
    @Autowired
    private RbacPermissionService rbacPermissionService;
    @Autowired
    private RbacRoleService rbacRoleService;


    @Override
    public UserVO register(RbacUserRegisterDTO rbacUserDTO) {
        return rbacUserService.register(rbacUserDTO);
    }

    @Override
    @DS("slave")
    public JwtToken login(String account, String password) {
        String[] accountItems = StringUtils.split(account, "@");
        // 账号元素
        String accountItem = accountItems[0];
        // 承租人元素（number或code）
        String tenantItem = accountItems[1];
        SysTenant tenant = sysTenantService.checkGetTenant(tenantItem);
        // 从数据库中取出用户信息
        RbacUser user = rbacUserService.getByAccountAndTenantId(accountItem,tenant.getId());
        // 断言用户存在
        ResponseEnum.ACCOUNT_NOT_FOUND.assertNotNull(user);
        // 断言密码正确
        ResponseEnum.ACCOUNT_PWD_ERROR.assertIsTrue(user.getPassword().equals(ShiroKitUtil.md5(password,user.getAccount())));

        // 添加权限
        List<RbacRole> rbacRoleList = rbacRoleService.findByUserId(user.getId());
        // 角色
        Set<String> roleCodes = rbacRoleList.stream().map(e -> e.getCode()).collect(Collectors.toSet());
        String[] roleCodeArray = new String[roleCodes.size()];
        roleCodes.toArray(roleCodeArray);
        // 权限
        String[] permissionArray = new String[4];
        roleCodes.toArray(roleCodeArray);
        if(CollectionUtils.isNotEmpty(roleCodes)){
            Set<Long> roleIds = rbacRoleList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            // 得到用户角色的所有角色所有的权限
            List<RbacPermission> permissionList = rbacPermissionService.findByRoleIds(roleIds);
            Set<String> permissionCodes = permissionList.stream().map(e -> e.getCode()).collect(Collectors.toSet());
            permissionArray =  new String[permissionCodes.size()];
            permissionCodes.toArray(permissionArray);
        }

        // 生成token
        JwtClaim jwtClaim = new JwtClaim();
        jwtClaim.setSubject(user.getId().toString());
        jwtClaim.setUserName(user.getName());
        jwtClaim.setAccount(user.getAccount());
        jwtClaim.setRoles(roleCodeArray);
        jwtClaim.setPermissions(permissionArray);
        jwtClaim.setTenantId(user.getTenantId());
        String accessToken = JwtUtil.generateToken(jwtClaim);
        JwtToken jwtUser = new JwtToken(accessToken,user.getId(),user.getName());
        return jwtUser;
    }

    @Override
    public void logout(String token) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        RedisUtil.del(token);
    }


}

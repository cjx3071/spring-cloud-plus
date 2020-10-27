/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package org.gourd.hu.rbac.auth.shiro;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.gourd.hu.base.exception.PreAuthorizeException;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.constant.HeaderConstant;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.JwtConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 身份校验核心类
 * @author gourd.hu
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        // 表示此Realm只支持JWTToken类型
        return token instanceof JwtToken;
    }

    /**
     * 默认使用此方法进行用户正确与否验证，错误抛出异常即可
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        String token = auth.getCredentials().toString();
        // 开始认证，要AccessToken认证通过
        if (StringUtils.isNotBlank(RedisUtil.getStr(token)) && JwtUtil.verify(RedisUtil.getStr(token))) {
            return new SimpleAuthenticationInfo(token, token, this.getClass().getName());
        }
        throw new PreAuthorizeException(ResponseEnum.TOKEN_CHECK_FAIL);
    }


    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Map<String, Claim> claims = getTokenClaimMap(principals);
        // 解析角色和权限
        List<String> roles = claims.get(JwtConstant.JWT_ROLES_KEY).asList(String.class);
        List<String> permissions = claims.get(JwtConstant.JWT_PERMISSIONS_KEY).asList(String.class);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }


    /**
     * 自定义权限校验
     * 增加超级管理员校验
     * 
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        HttpServletRequest httpServletRequest = RequestHolder.getRequest();
        String tokenIgnoreFlag = httpServletRequest.getHeader(HeaderConstant.HEADER_TOKEN_IGNORE);
        if(HeaderConstant.TOKEN_IGNORE_FLAG.equals(tokenIgnoreFlag)){
            return true;
        }
        Map<String, Claim> claims = getTokenClaimMap(principals);
        // 解析权限
        List<String> permissions = claims.get(JwtConstant.JWT_PERMISSIONS_KEY).asList(String.class);
        if(!permissions.contains(JwtConstant.ALL_PERMISSION) && !super.isPermitted(principals,permission)){
            throw new PreAuthorizeException(ResponseEnum.UNAUTHORIZED);
        }
        return true;
    }

    /**
     * 自定义角色校验
     * 增加超级管理员校验
     * 
     * @param principals
     * @param roleIdentifier
     * @return
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        HttpServletRequest httpServletRequest = RequestHolder.getRequest();
        String tokenIgnoreFlag = httpServletRequest.getHeader(HeaderConstant.HEADER_TOKEN_IGNORE);
        if(HeaderConstant.TOKEN_IGNORE_FLAG.equals(tokenIgnoreFlag)){
            return true;
        }
        Map<String, Claim> claims = getTokenClaimMap(principals);
        // 解析角色和权限
        List<String> roles = claims.get(JwtConstant.JWT_ROLES_KEY).asList(String.class);
        if(!roles.contains(JwtConstant.SUPPER_ADMIN_ROLE) && !super.hasRole(principals,roleIdentifier)){
            throw new PreAuthorizeException(ResponseEnum.UNAUTHORIZED);
        }
        return true;
    }

    /**
     * 获取当前用户ClaimMap
     * @param principals
     * @return
     */
    private Map<String, Claim> getTokenClaimMap(PrincipalCollection principals) {
        String accessToken = principals.toString();
        Map<String, Claim> claims = JwtUtil.getClaims(accessToken);
        if (MapUtils.isEmpty(claims)) {
            throw new PreAuthorizeException(ResponseEnum.TOKEN_CHECK_FAIL);
        }
        return claims;
    }

}

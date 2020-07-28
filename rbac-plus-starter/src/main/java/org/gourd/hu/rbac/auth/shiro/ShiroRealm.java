/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package org.gourd.hu.rbac.auth.shiro;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.JwtConstant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 身份校验核心类
 * @author gourd.hu
 */
@Slf4j
@Component
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
        String token = (String) auth.getCredentials();
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        String subject = JwtUtil.getSubject(token);
        if (JwtUtil.verify(token) && RedisUtil.existAny(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + subject)) {
            // 获取RefreshToken的时间戳
            Long currentTimeMillisRedis = Long.valueOf(RedisUtil.get(JwtConstant.PREFIX_SHIRO_REFRESH_TOKEN + subject).toString());
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaimLong(token,JwtConstant.JWT_CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, this.getClass().getName());
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
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

        String accessToken = principals.toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Map<String, Claim> claims = JwtUtil.getClaims(accessToken);
        if(MapUtils.isEmpty(claims)){
            throw new UnauthorizedException("Token已过期(Token expired or incorrect.)");
        }
        // 解析角色和权限
        List<String> roles = claims.get(JwtConstant.JWT_ROLES_KEY).asList(String.class);
        List<String> permissions = claims.get(JwtConstant.JWT_PERMISSIONS_KEY).asList(String.class);
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

}

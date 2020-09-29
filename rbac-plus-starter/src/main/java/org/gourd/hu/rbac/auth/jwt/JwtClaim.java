package org.gourd.hu.rbac.auth.jwt;

import lombok.Data;

/**
 * @Description JwtClaim
 * @Author gourd.hu
 * @Date 2020/3/10 15:10
 * @Version 1.0
 */
@Data
public class JwtClaim {

    /**
     * 此处为用户Id
     */
    private String subject;

    /**
     * 承租人id
     */
    private Long tenantId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 角色
     */
    private String[] roles;

    /**
     * 权限
     */
    private String[] permissions;
}

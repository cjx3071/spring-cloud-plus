package org.gourd.hu.rbac.service;


import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.model.dto.RbacUserRegisterDTO;
import org.gourd.hu.rbac.model.vo.UserVO;

/**
 * @author: gourd
 */
public interface AuthService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    UserVO register(RbacUserRegisterDTO user);

    /**
     * 登陆
     * @param account
     * @param password
     * @return
     */
    JwtToken login(String account, String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token);


}

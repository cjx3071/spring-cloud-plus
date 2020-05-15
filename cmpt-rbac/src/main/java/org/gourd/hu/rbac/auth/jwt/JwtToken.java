package org.gourd.hu.rbac.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author gourd.hu
 */
@Data
@NoArgsConstructor
public class JwtToken implements AuthenticationToken{

    /**
     * 密钥
     */
    private String accessToken;

    /**
     * 承租人id
     */
    private Long tenantId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;

    @Override
    @JsonIgnore
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        return accessToken;
    }

    public  JwtToken(String accessToken,Long userId,String userName){
        this.accessToken = accessToken;
        this.userId = userId;
        this.userName = userName;
    }
    public  JwtToken(String accessToken,Long tenantId,Long userId,String userName){
        this.accessToken = accessToken;
        this.tenantId=tenantId;
        this.userId = userId;
        this.userName = userName;
    }
    public JwtToken(String accessToken){
        this.accessToken = accessToken;
    }

}

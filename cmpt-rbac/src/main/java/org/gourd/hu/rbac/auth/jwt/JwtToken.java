package org.gourd.hu.rbac.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author gourd.hu
 */
@Data
@NoArgsConstructor
@ApiModel(value = "用户Token", description = "用户Token")
public class JwtToken implements AuthenticationToken{

    @ApiModelProperty("密钥")
    private String accessToken;

    @ApiModelProperty("承租人id")
    private Long tenantId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
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

package org.gourd.hu.rbac.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 权限属性
 *
 * @author gourd.hu
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    /**
     * 忽略校验
     */
    private String[] ignores;

    private JwtConfig jwt;

    @Data
    public static class JwtConfig {

        private String header;
        /**
         * 密钥
         */
        private String encryptJWTKey;

        /**
         * AccessToken过期时间
         */
        private Long accessTokenExpireTime ;

        /**
         * RefreshToken过期时间
         */
        private Long refreshTokenExpireTime;
        /**
         * Shiro缓存过期时间-5分钟-5*60(秒为单位)(一般设置与AccessToken过期时间一致)
         */
        private Long shiroCacheExpireTime;
    }

}


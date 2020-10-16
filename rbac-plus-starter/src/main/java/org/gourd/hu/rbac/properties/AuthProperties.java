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
     * 忽略校验的路径
     */
    private String[] ignores;
    /**
     * jwt相关配置
     */
    private JwtConfig jwt;

    @Data
    public static class JwtConfig {
        /**
         * jwt自定义请求头key
         */
        private String header;
        /**
         * 密钥
         */
        private String encryptJWTKey;
        /**
         * JWT过期时间（单位s）
         */
        private Long accessTokenExpireTime ;
        /**
         * JWT续期时间（单位s），即token过期后，此时间内操作会自动续期。
         */
        private Long refreshTokenExpireTime;
    }
}


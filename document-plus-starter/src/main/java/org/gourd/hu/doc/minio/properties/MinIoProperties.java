package org.gourd.hu.doc.minio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio配置属性
 *
 * @author gourd.hu
 */
@Data
@ConfigurationProperties(prefix = "min.io")
public class MinIoProperties {
    /**
     * Minio 服务地址
     */
    private String endpoint;

    /**
     * Minio ACCESS_KEY
     */
    private String accessKey;

    /**
     * Minio SECRET_KEY
     */
    private String secretKey;
}
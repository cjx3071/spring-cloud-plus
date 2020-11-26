package org.gourd.hu.doc.ftp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio配置属性
 *
 * @author gourd.hu
 */
@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    /**
     * ftp服务器ip
     */
    private String host;
    /**
     * ftp服务器端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 存放文件的基本路径
     */
    private String basePath;

    /**
     * 超时时间
     */
    private Integer timeOut = 60 * 1000;

    /**
     * 缓冲大小
     */
    private Integer buffer_size = 1024 * 1024;
}
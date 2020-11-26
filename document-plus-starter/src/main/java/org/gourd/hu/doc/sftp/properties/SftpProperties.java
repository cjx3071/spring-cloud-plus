package org.gourd.hu.doc.sftp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sftp配置
 *
 * @author wb
 */
@Getter
@Setter
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "sftp.client")
public class SftpProperties {
    /**
     * # ip地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 协议
     */
    private String protocol;
    /**
     * 用户
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 根目录
     */
    private String root;
    /**
     * 秘钥地址
     */
    private String privateKey;
    /**
     * 秘钥密码
     */
    private String passphrase;

    private String sessionStrictHostKeyChecking;
    /**
     * session连接超时时间
     */
    private Integer sessionConnectTimeout;
    /**
     * channel连接超时时间
     */
    private Integer channelConnectedTimeout;
}
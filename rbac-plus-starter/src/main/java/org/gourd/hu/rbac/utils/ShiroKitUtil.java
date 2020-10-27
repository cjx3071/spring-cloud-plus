package org.gourd.hu.rbac.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro工具类
 * @author gourd.hu
 */
public class ShiroKitUtil {

    public final static String hashAlgorithmName = "MD5";
 
    public final static int hashIterations = 1024;
 
    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("123456","HuXL"));
    }
}
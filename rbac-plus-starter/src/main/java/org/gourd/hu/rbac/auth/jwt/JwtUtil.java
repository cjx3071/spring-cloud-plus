package org.gourd.hu.rbac.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.utils.Base64ConvertUtil;
import org.gourd.hu.rbac.constant.JwtConstant;
import org.gourd.hu.rbac.properties.AuthProperties;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * jwt 工具类
 *
 * @author gourd.hu
 */
@Slf4j
public class JwtUtil {

    private AuthProperties authProperties;

    public JwtUtil(AuthProperties authProperties){
        this.authProperties = authProperties;
    }


    /**
     * 过期时间改为从配置文件获取
     */
    private static Long accessTokenExpireTime;
    /**
     * RefreshToken过期时间-30分钟-30*60(秒为单位)
     */
    private static Long refreshTokenExpireTime;

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    @PostConstruct
    public void init(){
        encryptJWTKey = authProperties.getJwt().getEncryptJWTKey();
        accessTokenExpireTime = authProperties.getJwt().getAccessTokenExpireTime();
        refreshTokenExpireTime = authProperties.getJwt().getRefreshTokenExpireTime();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static JwtToken getCurrentUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal == null ){
            return null;
        }
        String accessToken = principal.toString();
        Map<String, Claim> claims = getClaims(accessToken);
        Long userId = Long.valueOf(getSubject(accessToken));
        String username = claims.get(JwtConstant.JWT_USER_NAME).asString();
        Long tenantId = claims.get(JwtConstant.JWT_TENANT_ID).asLong();
        return new JwtToken(accessToken,tenantId,userId,username);
    }

    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author Wang926454
     * @date 2018/8/31 9:05
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getSubject(token) + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("JWTToken认证解密异常");
        }catch (TokenExpiredException e){
            throw new TokenExpiredException("JWTToken过期");
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     */
    public static Object getClaimString(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("解密Token中的公共信息异常");
        }
    }
    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     */
    public static Object getClaimLong(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asLong();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("解密Token中的公共信息异常");
        }
    }
    /**
     * 获得Token中的信息
     * @param token
     * @return java.lang.String
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaims();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("解密Token中的公共信息异常");
        }
    }


    /**
     * 获得Token中的subject，即userId
     * @param token
     * @return java.lang.String
     */
    public static String getSubject(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("解密Token中的公共信息异常");
        }
    }

    /**
     * 生成Token
     *
     * @param jwtClaim
     * @return 返回加密的Token
     */
    public static String generateToken(JwtClaim jwtClaim) {
        try {
            // 帐号加JWT私钥加密
            String secret = jwtClaim.getSubject() + Base64ConvertUtil.decode(encryptJWTKey);
            long currentTimeMillis = System.currentTimeMillis();
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date( currentTimeMillis + accessTokenExpireTime * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withClaim(JwtConstant.JWT_USER_NAME, jwtClaim.getUserName())
                    .withClaim(JwtConstant.JWT_USER_ACCOUNT, jwtClaim.getAccount())
                    .withArrayClaim(JwtConstant.JWT_ROLES_KEY, jwtClaim.getRoles())
                    .withArrayClaim(JwtConstant.JWT_PERMISSIONS_KEY, jwtClaim.getPermissions())
                    .withClaim(JwtConstant.JWT_CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withClaim(JwtConstant.JWT_TENANT_ID, jwtClaim.getTenantId())
                    .withSubject(jwtClaim.getSubject())
                    .withExpiresAt(date)
                    .sign(algorithm);
            // 设置到redis缓存,key和value均为jwt-token,过期时间设置为 过期时间 + 续期时间
            RedisUtil.setStrExpire(token, token,accessTokenExpireTime + refreshTokenExpireTime);
            return token;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw ResponseEnum.INTERNAL_SERVER_ERROR.newException("JWTToken加密异常");
        }
    }

    /**
     * 刷新token
     * @param token
     * @return java.lang.String 返回加密的Token
     */
    public static String refreshToken(String token) {
        String subject = getSubject(token);
        // 获取token的属性
        Map<String, Claim> claims = getClaims(token);
        JwtClaim jwtClaim = new JwtClaim();
        jwtClaim.setSubject(subject);
        jwtClaim.setUserName(claims.get(JwtConstant.JWT_USER_NAME).asString());
        jwtClaim.setAccount(claims.get(JwtConstant.JWT_USER_ACCOUNT).asString());
        jwtClaim.setRoles(claims.get(JwtConstant.JWT_ROLES_KEY).asArray(String.class));
        jwtClaim.setPermissions(claims.get(JwtConstant.JWT_PERMISSIONS_KEY).asArray(String.class));
        jwtClaim.setTenantId(claims.get(JwtConstant.JWT_TENANT_ID).asLong());
        // 重新生成token
        return generateToken(jwtClaim);
    }

    /**
     * 续期token
     * @param oldToken
     * @return java.lang.String 返回加密的Token
     */
    public static Boolean reNewToken(String oldToken) {
        // 重新生成token
        String refreshToken = refreshToken(oldToken);
        // 续期原token的过期时间，并更新value为新token
        RedisUtil.setStrExpire(oldToken,refreshToken,accessTokenExpireTime + refreshTokenExpireTime);
        return Boolean.TRUE;
    }

}
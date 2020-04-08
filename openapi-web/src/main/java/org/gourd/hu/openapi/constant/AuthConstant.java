package org.gourd.hu.openapi.constant;

/**
 * @Description 密钥
 * @Author gourd
 * @Date 2020/4/2 13:39
 * @Version 1.0
 */
public class AuthConstant {

    /** 字符编码 */
    public final static String INPUT_CHARSET = "UTF-8";
    /** 签名 */
    public final static String SIGN_KEY = "signature";
    /** 时间戳 */
    public final static String TIMESTAMP_KEY = "timestamp";
    /** 随机数 */
    public final static String NONCE_KEY = "nonce";
    /** appKey */
    public final static String ACCESS_KEY = "appKey";
    /** secret */
    public final static String SECRET_KEY = "secretKey";
    /** 过期时间 */
    public final static String EXPIRED_KEY = "expireTimes";

    /** 提示 */
    public final static String PARAM_ERROR = "请求参数不合法";
    public final static String SIGN_ERROR = "签名验证失败";
    public final static String APP_KEY_ERROR = "appKey错误";
    public final static String SIGN_EXPIRED = "请求已经失效";
}

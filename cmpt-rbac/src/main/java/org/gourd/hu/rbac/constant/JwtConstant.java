package org.gourd.hu.rbac.constant;

/**
 *  全局常量类
 *
 * @author gourd.hu
 * @version：1.0.0
 */
public class JwtConstant {

    public static final String REQUEST_AUTH_HEADER="Authorization";
    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN="access_token";
    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN="refresh_token";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String JWT_CURRENT_TIME_MILLIS = "jwt-current-time-millis-key";

    /**
     * 权限key
     */
    public static final String JWT_PERMISSIONS_KEY="jwt-permissions-key";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_ACCOUNT="jwt-user-account-key";
    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 承租人 key
     */
    public static final String JWT_TENANT_ID="jwt-tenant-id-key";

    /**
     * 角色key
     */
    public static final String JWT_ROLES_KEY="jwt-roles-key";

    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key";

    /**
     *  刷新状态(适用场景如：一个功能点要一次性请求多个接口，当第一个请求刷新接口时候 加入一个节点下一个接口请求进来的时候直接放行)
     */
    public static final String JWT_REFRESH_STATUS="jwt-refresh-status";

    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION="jwt-refresh-identification";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST="jwt-refresh-token-blacklist";

    /**
     * 组织机构编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key";

    /**
     * 菜单权限编码key
     */
    public static final String PERMISSION_CODE_KEY="permission-code-key";
    /**
     * redis-OK
     */
    public static final String OK = "OK";
    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";


}

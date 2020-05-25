package org.gourd.hu.base.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gourd.hu.base.exception.assertion.BusinessExceptionAssert;

/**
 * <p>返回结果</p>
 *
 * @author gourd.hu
 * @date 2018.09.17
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {

    OK(200, "成功."),
    INTERNAL_SERVER_ERROR(500, "系统异常."),
    BAD_REQUEST(400,"参数不合法."),
    UNAUTHORIZED(401,"无权限."),
    FORBIDDEN(403,"拒绝访问."),
    NOT_FOUND(404, "未找到."),
    METHOD_NOT_ALLOWED(405, "方法不支持."),
    ILLEGAL_ACCESS(406, "非法获取."),
    REPEAT_COMMIT(111, "重复提交."),
    DATA_NOT_FOUND(6000, "数据不存在或已被删除."),
    ACCOUNT_NOT_FOUND(6001, "账号未注册."),
    ACCOUNT_BEEN_USED(6002, "此账号已被注册."),
    ACCOUNT_PWD_ERROR(6003, "账号或密码错误."),
    TENANT_NOT_FOUND(6004, "承租人信息不存在或已被停用."),
    FILE_TOO_LARGE(6014, "文件大小超出10MB限制, 请压缩或降低文件质量."),
    FIELD_TOO_LARGE(6013, "字段太长,超出数据库字段的长度."),

    TEMPLATE_PARSE_ERROR(6015, "模板解析错误."),
    TEMPLATE_DATA_NULL(6016, "模板参数为空."),



    SIGN_ERROR(7001, "签名验证失败."),
    APP_KEY_ERROR(7002, "appKey错误."),
    SIGN_EXPIRED(7003, "请求已经失效."),
    ;

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;
}

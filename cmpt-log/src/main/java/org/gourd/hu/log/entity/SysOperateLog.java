package org.gourd.hu.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.base.common.entity.BaseEntity;

import java.util.Date;

/**
 * @author gourd
 * @date 2018-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_operate_log")
public class SysOperateLog extends BaseEntity {

    /**
     * 模块名
     */
    @TableField(value = "module_name")
    private String moduleName;
    /**
     * 业务名
     */
    @TableField(value = "business_name")
    private String businessName;
    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 方法名
     */
    @TableField(value = "method_name")
    private String methodName;

    /**
     * 方法类型
     */
    @TableField(value = "method_type")
    private String methodType;

    /**
     * 请求路径
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 请求ip
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 请求耗时
     */
    @TableField(value = "request_time")
    private Long requestTime;


    /**
     * 返回结果
     */
    @TableField(value = "response_detail")
    private String responseDetail;

    /**
     * 异常详细
     */
    @TableField(value = "exception_detail")
    private String exceptionDetail;

    /**
     * 过期时间
     */
    @TableField(value = "expire_time")
    private Date expireTime;
}

package org.gourd.hu.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gourd.hu.core.base.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志记录表
 * </p>
 *
 * @author gourd.hu
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_operate_log")
@ApiModel(value = "SysOperateLog对象", description = "操作日志记录表")
public class SysOperateLog extends BaseEntity<SysOperateLog> {

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "业务名")
    private String businessName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "方法类型")
    private String methodType;

    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "请求ip")
    private String requestIp;

    @ApiModelProperty(value = "请求耗时")
    private Long requestTime;

    @ApiModelProperty(value = "返回结果")
    private String responseDetail;

    @ApiModelProperty(value = "异常详细")
    private String exceptionDetail;

    @ApiModelProperty(value = "过期删除时间")
    private LocalDateTime expireTime;

}

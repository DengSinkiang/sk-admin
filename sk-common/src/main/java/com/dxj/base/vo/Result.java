package com.dxj.base.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sinkiang
 * 前后端交互数据标准
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message;

    /**
     * 返回状态码
     */
    @ApiModelProperty(value = "返回状态码")
    private int code;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    /**
     * 返回数据对象
     */
    @ApiModelProperty(value = "返回数据对象")
    private T result;
}

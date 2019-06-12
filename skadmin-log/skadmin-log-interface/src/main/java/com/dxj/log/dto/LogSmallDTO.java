package com.dxj.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: dxj
 * @Date: 2019-05-27 09:45
 */
@Data
public class LogSmallDTO implements Serializable {

    /**
     * 描述
     */
    private String description;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 创建日期
     */
    private Timestamp createTime;
}

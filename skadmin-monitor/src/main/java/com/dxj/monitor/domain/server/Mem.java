package com.dxj.monitor.domain.server;

import lombok.Data;

/**
 * 内存信息
 */

@Data
public class Mem {

    /**
     * 内存总量
     */
    private String total;

    /**
     * 已用内存
     */
    private String used;

    /**
     * 剩余内存
     */
    private String free;

    /**
     * JVM空闲内存使用率(M)
     */
    private Integer usedPercent;

}

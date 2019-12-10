package com.dxj.monitor.entity.server;

import lombok.Data;

/**
 * cpu信息
 */

@Data
public class Cpu {

    /**
     * 核心数
     */
    private Integer cpuNum;

    /**
     * CPU用户使用率
     */
    private Integer usedPercent;

}

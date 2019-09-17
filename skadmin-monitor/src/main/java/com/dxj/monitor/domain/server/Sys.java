package com.dxj.monitor.domain.server;

import lombok.Data;

/**
 * 服务器系统信息
 */

@Data
public class Sys {

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器Ip
     */
    private String serverIp;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

}

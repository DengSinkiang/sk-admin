package com.dxj.monitor.entity.server;

import lombok.Data;

/**
 * App项目应用 信息
 */

@Data
public class App {

    /**
     * 项目路径
     */
    private String appDir;

    /**
     * 日志存放路径
     */
    private String logDir;

    /**
     * 上传文件路径
     */
    private String uploadDir;

}

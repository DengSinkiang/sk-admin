package com.dxj.monitor.domain;

import lombok.Data;

/**
 * 🙃
 * 🙃 App项目应用 信息
 * 🙃
 *
 * @author:zhaozhenghao
 * @Email :736720794@qq.com
 * @date :2019/04/06 22:36
 * App.java
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

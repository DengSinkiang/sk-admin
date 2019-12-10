package com.dxj.monitor.entity.server;

import lombok.Data;

/**
 * 系统磁盘信息
 */

@Data
public class SysDisk {

    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String diskType;

    /**
     * 文件系统
     */
    private String fileSystem;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private String usedPercent;

}

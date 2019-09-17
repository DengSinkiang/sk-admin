package com.dxj.monitor.domain.server;

import com.dxj.common.util.DateUtils;
import lombok.Data;

/**
 * jvm信息
 */

@Data
public class Jvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    private String total;

    /**
     * JVM最大可用内存总数(M)
     */
    private String max;

    /**
     * JVM空闲内存(M)
     */
    private String free;

    /**
     * JVM空闲内存(M)
     */
    private String used;

    /**
     * JVM空闲内存使用率(M)
     */
    private Integer usedPercent;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    /**
     * Java名称
     */
    private String name;


    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }
}

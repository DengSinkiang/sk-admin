package com.dxj.monitor.domain;

/**
 * 🙃
 * 🙃 内存信息
 * 🙃
 *
 * @author:zhaozhenghao
 * @Email :736720794@qq.com
 * @date :2019/04/06 17:28
 * Mem.java
 */

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public Integer getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(Integer usedPercent) {
        this.usedPercent = usedPercent;
    }
}

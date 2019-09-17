package com.dxj.monitor.domain;

/**
 * 🙃
 * 🙃 cpu信息
 * 🙃
 *
 * @author:zhaozhenghao
 * @Email :736720794@qq.com
 * @date :2019/04/06 17:27
 * Cpu.java
 */

public class Cpu {

    /**
     * 核心数
     */
    private Integer cpuNum;

    /**
     * CPU用户使用率
     */
    private Integer usedPercent;

    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    public Integer getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(Integer usedPercent) {
        this.usedPercent = usedPercent;
    }
}

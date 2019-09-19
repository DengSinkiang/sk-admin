package com.dxj.monitor.domain.server;

import com.dxj.common.util.CommonUtil;
import com.dxj.common.util.FileUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * 服务器实时信息
 */
@Component
@Data
public class ServerInstantEntity {

    private static final Logger logger = LoggerFactory.getLogger(ServerInstantEntity.class);

    private static SystemInfo systemInfo = new SystemInfo();

    /**
     * Cpu相关信息
     */
    private Cpu cpu = new Cpu();

    /**
     * 内存相关信息
     */
    private Mem mem = new Mem();

    /**
     * JVM相关信息
     */
    private Jvm jvm = new Jvm();

    public void init() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        setCpuInfo(hal.getProcessor());
        setMemInfo(hal.getMemory());
        setJvmInfo();
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setUsedPercent((int) (processor.getSystemCpuLoad() * 100));
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(FileUtil.convertFileSize(memory.getTotal()));
        mem.setUsed(FileUtil.convertFileSize(memory.getTotal() - memory.getAvailable()));
        mem.setFree(FileUtil.convertFileSize(memory.getAvailable()));
        int percent = 0;
        try {
            percent = Integer.parseInt(CommonUtil.accuracy((double) (memory.getTotal() - memory.getAvailable()), (double) memory.getTotal(), 0));
        } catch (NumberFormatException e) {
            logger.error("percent number format exception", e);
        }
        mem.setUsedPercent(percent);
    }

    /**
     * 设置JVM信息
     */
    private void setJvmInfo() {
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        long max = Runtime.getRuntime().maxMemory();
        jvm.setTotal(FileUtil.convertFileSize(total));
        jvm.setUsed(FileUtil.convertFileSize(total - free));
        jvm.setFree(FileUtil.convertFileSize(free));
        jvm.setMax(FileUtil.convertFileSize(max));
        int percent = 0;
        try {
            percent = Integer.parseInt(CommonUtil.accuracy((double) (total - free), (double) total, 0));
        } catch (NumberFormatException e) {
            logger.error("percent number format exception", e);
        }
        jvm.setUsedPercent(percent);
    }
}

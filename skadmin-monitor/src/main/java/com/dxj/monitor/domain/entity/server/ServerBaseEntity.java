package com.dxj.monitor.domain.entity.server;

import com.dxj.common.util.CommonUtil;
import com.dxj.common.util.FileUtil;
import com.dxj.common.util.IpInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 服务器基础信息
 */
@Component
public class ServerBaseEntity {

    private static final Logger logger = LoggerFactory.getLogger(ServerBaseEntity.class);

    private static SystemInfo systemInfo = new SystemInfo();

    /**
     * JVM相关信息
     */
    private App app = new App();

    /**
     * JVM相关信息
     */
    private Jvm jvm = new Jvm();

    /**
     * 服务器相关信息
     */
    private Sys sys = new Sys();

    /**
     * 磁盘相关信息
     */
    private List<SysDisk> sysDisks = new LinkedList<>();

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Jvm getJvm() {
        return jvm;
    }

    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<SysDisk> getSysDisks() {
        return sysDisks;
    }

    public void setSysDisks(List<SysDisk> sysDisks) {
        this.sysDisks = sysDisks;
    }

    public void init() {
        Properties props = System.getProperties();
        setJvmInfo(props);
        setSysInfo(props);
        setSysFiles(systemInfo.getOperatingSystem());
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo(Properties props) {
        jvm.setName(props.getProperty("java.vm.name"));
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo(Properties props) {
        sys.setServerName(IpInfoUtil.getHostName());
        sys.setServerIp(IpInfoUtil.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        sysDisks.clear();
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysDisk sysDisk = new SysDisk();
            sysDisk.setDirName(fs.getMount());
            sysDisk.setDiskType(fs.getType());
            sysDisk.setFileSystem(fs.getName());
            sysDisk.setTotal(FileUtil.convertFileSize(total));
            sysDisk.setFree(FileUtil.convertFileSize(free));
            sysDisk.setUsed(FileUtil.convertFileSize(used));
            int percent = 0;
            try {
                percent = Integer.parseInt(CommonUtil.accuracy(used, total, 0));
            } catch (NumberFormatException e) {
                logger.error("percent number format exception", e);
            }
            sysDisk.setUsedPercent(percent + "%");
            sysDisks.add(sysDisk);
        }
    }
}

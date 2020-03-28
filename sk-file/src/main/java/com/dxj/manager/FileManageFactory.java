package com.dxj.manager;

import cn.hutool.core.util.StrUtil;
import com.dxj.constant.CommonConstant;
import com.dxj.constant.SettingConstant;
import com.dxj.domain.entity.Setting;
import com.dxj.exception.SkException;
import com.dxj.manager.impl.*;
import com.dxj.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工厂模式
 *
 * @author Sinkiang
 */
@Component
public class FileManageFactory {

    private final SettingService settingService;

    private final QiNiuFileManage qiNiuFileManage;

    private final AliFileManage aliFileManage;

    private final TencentFileManage tencentFileManage;

    private final MinIOFileManage minIOFileManage;

    private final LocalFileManage localFileManage;

    @Autowired
    public FileManageFactory(SettingService settingService, QiNiuFileManage qiNiuFileManage, AliFileManage aliFileManage, TencentFileManage tencentFileManage, MinIOFileManage minIOFileManage, LocalFileManage localFileManage) {
        this.settingService = settingService;
        this.qiNiuFileManage = qiNiuFileManage;
        this.aliFileManage = aliFileManage;
        this.tencentFileManage = tencentFileManage;
        this.minIOFileManage = minIOFileManage;
        this.localFileManage = localFileManage;
    }

    /**
     * 使用配置的服务上传时location传入null 管理文件时需传入存储位置location
     *
     * @param location
     * @return
     */
    public FileManage getFileManage(int location) {

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new SkException("您还未配置OSS存储服务");
        }
        String type = setting.getValue();
        if ((type.equals(SettingConstant.QINIU_OSS)) || CommonConstant.OSS_QINIU == location) {
            return qiNiuFileManage;
        } else if ((type.equals(SettingConstant.ALI_OSS)) || CommonConstant.OSS_ALI == location) {
            return aliFileManage;
        } else if ((type.equals(SettingConstant.TENCENT_OSS)) || CommonConstant.OSS_TENCENT == location) {
            return tencentFileManage;
        } else if ((type.equals(SettingConstant.MINIO_OSS)) || CommonConstant.OSS_MINIO == location) {
            return minIOFileManage;
        } else if ((type.equals(SettingConstant.LOCAL_OSS)) || CommonConstant.OSS_LOCAL == location) {
            return localFileManage;
        } else {
            throw new SkException("暂不支持该存储配置，请检查配置");
        }
    }
}

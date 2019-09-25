package com.dxj.file.manage;

import cn.hutool.core.util.StrUtil;
import com.dxj.common.constant.CommonConstant;
import com.dxj.common.constant.SettingConstant;
import com.dxj.common.exception.BadRequestException;
import com.dxj.file.manage.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工厂模式
 *
 * @author Sinkiang
 */
@Component
public class FileManageFactory {

    @Autowired
    private SettingService settingService;

    @Autowired
    private QiniuFileManage qiniuFileManage;

    @Autowired
    private AliFileManage aliFileManage;

    @Autowired
    private TencentFileManage tencentFileManage;

    @Autowired
    private MinioFileManage minioFileManage;

    @Autowired
    private LocalFileManage localFileManage;

    /**
     * 使用配置的服务上传时location传入null 管理文件时需传入存储位置location
     *
     * @param location
     * @return
     */
    public FileManage getFileManage(Integer location) {

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new BadRequestException("您还未配置OSS存储服务");
        }
        String type = setting.getValue();
        if ((type.equals(SettingConstant.QINIU_OSS) && location == null) || CommonConstant.OSS_QINIU.equals(location)) {
            return qiniuFileManage;
        } else if ((type.equals(SettingConstant.ALI_OSS) && location == null) || CommonConstant.OSS_ALI.equals(location)) {
            return aliFileManage;
        } else if ((type.equals(SettingConstant.TENCENT_OSS) && location == null) || CommonConstant.OSS_TENCENT.equals(location)) {
            return tencentFileManage;
        } else if ((type.equals(SettingConstant.MINIO_OSS) && location == null) || CommonConstant.OSS_MINIO.equals(location)) {
            return minioFileManage;
        } else if ((type.equals(SettingConstant.LOCAL_OSS) && location == null) || CommonConstant.OSS_LOCAL.equals(location)) {
            return localFileManage;
        } else {
            throw new BadRequestException("暂不支持该存储配置，请检查配置");
        }
    }
}

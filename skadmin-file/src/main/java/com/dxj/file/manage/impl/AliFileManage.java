package com.dxj.file.manage.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.dxj.common.constant.SettingConstant;
import com.dxj.common.exception.BadRequestException;
import com.dxj.file.manage.FileManage;
import com.dxj.tool.entity.Setting;
import com.dxj.tool.entity.vo.OssSetting;
import com.dxj.tool.service.SettingService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author Sinkiang
 */
@Component
public class AliFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.ALI_OSS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new BadRequestException("您还未配置阿里云OSS存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    @Override
    public String pathUpload(String filePath, String key) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.putObject(os.getBucket(), key, new File(filePath));
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + key;
    }

    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.putObject(os.getBucket(), key, inputStream);
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + key;
    }

    @Override
    public String renameFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        copyFile(fromKey, toKey);
        deleteFile(fromKey);
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + toKey;
    }

    @Override
    public String copyFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.copyObject(os.getBucket(), fromKey, os.getBucket(), toKey);
        ossClient.shutdown();
        return os.getHttp() + os.getBucket() + "." + os.getEndpoint() + "/" + toKey;
    }

    @Override
    public void deleteFile(String key) {

        OssSetting os = getOssSetting();
        OSSClient ossClient = new OSSClient(os.getHttp() + os.getEndpoint(), new DefaultCredentialProvider(os.getAccessKey(), os.getSecretKey()), null);
        ossClient.deleteObject(os.getBucket(), key);
        ossClient.shutdown();
    }
}

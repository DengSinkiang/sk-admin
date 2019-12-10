package com.dxj.file.manage.impl;

import cn.hutool.core.util.StrUtil;
import com.dxj.common.constant.SettingConstant;
import com.dxj.common.exception.BadRequestException;
import com.dxj.file.manage.FileManage;
import com.dxj.tool.entity.Setting;
import com.dxj.tool.entity.vo.OssSetting;
import com.dxj.tool.service.SettingService;
import com.google.gson.Gson;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Sinkiang
 */
@Component
public class MinioFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.MINIO_OSS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new BadRequestException("您还未配置MinIO存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    /**
     * 如果存储桶不存在 创建存储通
     *
     * @param os
     * @param minioClient
     * @throws Exception
     */
    private void checkBucket(OssSetting os, MinioClient minioClient) throws Exception {

        // 如果存储桶不存在 创建存储通
        if (!minioClient.bucketExists(os.getBucket())) {
            minioClient.makeBucket(os.getBucket());
            // 设置隐私权限 公开读
            String builder = "{\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetBucketLocation\",\n" +
                    "                \"s3:ListBucket\"\n" +
                    "            ],\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": \"*\",\n" +
                    "            \"Resource\": \"arn:aws:s3:::" + os.getBucket() + "\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"Action\": \"s3:GetObject\",\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": \"*\",\n" +
                    "            \"Resource\": \"arn:aws:s3:::" + os.getBucket() + "/*\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"Version\": \"2012-10-17\"\n" +
                    "}\n";
            minioClient.setBucketPolicy(os.getBucket(), builder);
        }
    }

    @Override
    public String pathUpload(String filePath, String key) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = new MinioClient(os.getHttp() + os.getEndpoint(), os.getAccessKey(), os.getSecretKey());
            checkBucket(os, minioClient);
            minioClient.putObject(os.getBucket(), key, filePath, null, null, null, null);
        } catch (Exception e) {
            throw new BadRequestException("上传出错，请检查MinIO配置");
        }
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + key;
    }

    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = new MinioClient(os.getHttp() + os.getEndpoint(), os.getAccessKey(), os.getSecretKey());
            checkBucket(os, minioClient);
            minioClient.putObject(os.getBucket(), key, inputStream, file.getSize(), null, null, file.getContentType());
        } catch (Exception e) {
            throw new BadRequestException("上传出错，请检查MinIO配置");
        }
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + key;
    }

    @Override
    public String renameFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        copyFile(fromKey, toKey);
        deleteFile(fromKey);
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + toKey;
    }

    @Override
    public String copyFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = new MinioClient(os.getHttp() + os.getEndpoint(), os.getAccessKey(), os.getSecretKey());
            checkBucket(os, minioClient);
            minioClient.copyObject(os.getBucket(), fromKey, os.getBucket(), toKey);
        } catch (Exception e) {
            throw new BadRequestException("拷贝文件出错，请检查MinIO配置");
        }
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + toKey;
    }

    @Override
    public void deleteFile(String key) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = new MinioClient(os.getHttp() + os.getEndpoint(), os.getAccessKey(), os.getSecretKey());
            checkBucket(os, minioClient);
            minioClient.removeObject(os.getBucket(), key);
        } catch (Exception e) {
            throw new BadRequestException("删除文件出错，请检查MinIO配置");
        }
    }
}

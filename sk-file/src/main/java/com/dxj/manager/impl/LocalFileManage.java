package com.dxj.manager.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dxj.constant.SettingConstant;
import com.dxj.domain.entity.Setting;
import com.dxj.domain.vo.OssSetting;
import com.dxj.exception.SkException;
import com.dxj.manager.FileManage;
import com.dxj.service.SettingService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Sinkiang
 */
@Slf4j
@Component
public class LocalFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.LOCAL_OSS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new SkException("您还未配置本地存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    @Override
    @Deprecated
    public String pathUpload(String filePath, String key) {

        throw new SkException("暂不支持");
    }

    /**
     * 实则为路径上传
     *
     * @param inputStream
     * @param key
     * @param file
     * @return
     */
    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        String yearMonth = DateUtil.format(DateUtil.date(), "yyyyMM");
        String day = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String path = os.getFilePath() + "/" + yearMonth + "/" + day;
        Path dir = Paths.get(path);
        //File dir = new File(path);
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path f = Paths.get(path + "/" + key);
        if (Files.exists(f)) {
            throw new SkException("文件名已存在");
        }
        try {
            file.transferTo(f);
            return path + "/" + key;
        } catch (IOException e) {
            log.error(e.toString());
            throw new SkException("上传文件出错");
        }
    }

    /**
     * 注意此处需传入url
     *
     * @param url
     * @param toKey
     * @return
     */
    @Override
    public String renameFile(String url, String toKey) {

        String result = copyFile(url, toKey);
        deleteFile(url);
        return result;
    }

    /**
     * 注意此处需传入url
     *
     * @param url
     * @param toKey
     * @return
     */
    @Override
    public String copyFile(String url, String toKey) {

        File file = new File(url);
        FileInputStream i;
        FileOutputStream o;

        try {
            i = new FileInputStream(file);
            o = new FileOutputStream(new File(file.getParentFile() + "/" + toKey));

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = i.read(buf)) > 0) {
                o.write(buf, 0, bytesRead);
            }

            i.close();
            o.close();
            return file.getParentFile() + "/" + toKey;
        } catch (IOException e) {
            log.error(e.toString());
            throw new SkException("复制文件出错");
        }
    }

    /**
     * 注意此处需传入url
     *
     * @param url
     */
    @Override
    public void deleteFile(String url) {

        try {
            Files.delete(Paths.get(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     *
     * @param url
     * @param response
     */
    public static void view(String url, HttpServletResponse response) {

        File file = new File(url);
        FileInputStream in = null;
        OutputStream out = null;

        try {
            if (file.exists()) {
                in = new FileInputStream(file);
                out = response.getOutputStream();

                byte[] buf = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buf)) > 0) {
                    out.write(buf, 0, bytesRead);
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new SkException("上传文件异常" + e);
        } finally {
            if (out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

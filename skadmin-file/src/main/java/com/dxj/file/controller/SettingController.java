package com.dxj.file.controller;

import cn.hutool.core.util.StrUtil;
import com.dxj.common.constant.SettingConstant;
import com.dxj.common.util.ResultUtil;
import com.dxj.common.vo.Result;
import com.dxj.file.entity.Setting;
import com.dxj.file.service.SettingService;
import com.dxj.file.vo.OssSetting;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sinkiang
 */
@Slf4j
@RestController
@Api(description = "基本配置接口")
@RequestMapping("/xboot/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "/seeSecret/{settingName}", method = RequestMethod.GET)
    @ApiOperation(value = "查看私密配置")
    public Result<Object> seeSecret(@PathVariable String settingName) {

        String result = "";
        Setting setting = settingService.get(settingName);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return new ResultUtil<>().setErrorMsg("配置不存在");
        }
        switch (settingName) {
            case SettingConstant.QINIU_OSS:
            case SettingConstant.ALI_OSS:
            case SettingConstant.TENCENT_OSS:
            case SettingConstant.MINIO_OSS:
                result = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
                break;

        }
        return new ResultUtil<>().setData(result);
    }

    @RequestMapping(value = "/oss/check", method = RequestMethod.GET)
    @ApiOperation(value = "检查OSS配置")
    public Result<Object> ossCheck() {

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return new ResultUtil<>().setErrorMsg(501, "您还未配置第三方OSS服务");
        }
        return new ResultUtil<>().setData(setting.getValue());
    }

    @RequestMapping(value = "/oss/{serviceName}", method = RequestMethod.GET)
    @ApiOperation(value = "查看OSS配置")
    public Result<OssSetting> oss(@PathVariable String serviceName) {

        Setting setting = new Setting();
        if (serviceName.equals(SettingConstant.QINIU_OSS) || serviceName.equals(SettingConstant.ALI_OSS)
                || serviceName.equals(SettingConstant.TENCENT_OSS) || serviceName.equals(SettingConstant.MINIO_OSS)
                || serviceName.equals(SettingConstant.LOCAL_OSS)) {
            setting = settingService.get(serviceName);
        }
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return new ResultUtil<OssSetting>().setData(null);
        }
        OssSetting ossSetting = new Gson().fromJson(setting.getValue(), OssSetting.class);
        ossSetting.setSecretKey("**********");
        return new ResultUtil<OssSetting>().setData(ossSetting);
    }

    @RequestMapping(value = "/oss/set", method = RequestMethod.POST)
    @ApiOperation(value = "OSS配置")
    public Result<Object> ossSet(@ModelAttribute OssSetting ossSetting) {

        String name = ossSetting.getServiceName();
        Setting setting = settingService.get(name);
        if (name.equals(SettingConstant.QINIU_OSS) || name.equals(SettingConstant.ALI_OSS)
                || name.equals(SettingConstant.TENCENT_OSS) || name.equals(SettingConstant.MINIO_OSS)) {

            // 判断是否修改secrectKey 保留原secrectKey 避免保存***加密字符
            if (StrUtil.isNotBlank(setting.getValue()) && !ossSetting.getChanged()) {
                String secrectKey = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
                ossSetting.setSecretKey(secrectKey);
            }
        }
        setting.setValue(new Gson().toJson(ossSetting));
        settingService.saveOrUpdate(setting);
        Setting used = settingService.get(SettingConstant.OSS_USED);
        used.setValue(name);
        settingService.saveOrUpdate(used);
        return new ResultUtil<>().setData(null);
    }


}

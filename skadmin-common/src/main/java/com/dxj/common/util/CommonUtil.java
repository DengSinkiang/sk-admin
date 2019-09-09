package com.dxj.common.util;

import com.dxj.common.constant.SettingConstant;

import java.util.Random;
import java.util.UUID;

/**
 * @author Sinkiang
 */
public class CommonUtil {

    /**
     * 以UUID重命名
     *
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + extName;
    }

    /**
     * 获得短信模版Key
     * @param type
     * @return
     */
    public static String getSmsTemplate(Integer type){
        switch (type){
            case 0:
                return SettingConstant.ALI_SMS_COMMON;
            case 1:
                return SettingConstant.ALI_SMS_REGIST;
            case 2:
                return SettingConstant.ALI_SMS_LOGIN;
            case 3:
                return SettingConstant.ALI_SMS_CHANGE_MOBILE;
            case 4:
                return SettingConstant.ALI_SMS_CHANG_PASS;
            case 5:
                return SettingConstant.ALI_SMS_RESET_PASS;
            case 6:
                return SettingConstant.ALI_SMS_ACTIVITI;
            default:
                return SettingConstant.ALI_SMS_COMMON;
        }
    }

    /**
     * 随机6位数生成
     */
    public static String getRandomNum() {

        Random random = new Random();
        int num = random.nextInt(999999);
        //不足六位前面补0
        return String.format("%06d", num);
    }

    /**
     * 批量递归删除时 判断target是否在ids中 避免重复删除
     * @param target
     * @param ids
     * @return
     */
    public static Boolean judgeIds(String target, String[] ids){

        boolean flag = false;
        for(String id : ids){
            if(id.equals(target)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}

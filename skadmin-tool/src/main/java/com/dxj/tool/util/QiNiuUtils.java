package com.dxj.tool.util;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.dxj.common.util.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 七牛云存储工具类
 * @author dxj
 * @date 2018-12-31
 */
public class QiNiuUtils {

    private static final String HUAD = "华东";

    private static final String HUAB = "华北";

    private static final String HUAN = "华南";

    private static final String BEIM = "北美";

    /**
     * 得到机房的对应关系
     * @param zone
     * @return
     */
    public static Configuration getConfiguration(String zone){

        if(HUAD.equals(zone)){
            return new Configuration(Zone.zone0());
        } else if(HUAB.equals(zone)){
            return new Configuration(Zone.zone1());
        } else if(HUAN.equals(zone)){
            return new Configuration(Zone.zone2());
        } else if (BEIM.equals(zone)){
            return new Configuration(Zone.zoneNa0());

            // 否则就是东南亚
        } else {
            return new Configuration(Zone.zoneAs0());
        }
    }

    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     * @param file
     * @return
     */
    public static String getKey(String file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return FileUtils.getFileNameNoEx(file) + "-" +
                sdf.format(date) +
                "." +
                FileUtils.getExtensionName(file);
    }
}

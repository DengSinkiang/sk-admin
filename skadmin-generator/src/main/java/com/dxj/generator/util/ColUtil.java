package com.dxj.generator.util;

import org.apache.commons.configuration.*;

/**
 * sql字段转java
 *
 * @author dxj
 * @date 2019-01-03
 */
class ColUtil {

    /**
     * 转换mysql数据类型为java数据类型
     *
     * @param type
     * @return
     */
    static String cloToJava(String type) {
        Configuration config = getConfig();
        assert config != null;
        return config.getString(type, "unknowType");
    }

    /**
     * 获取配置信息
     */
    private static PropertiesConfiguration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.dxj.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 🙃
 * 🙃 数据处理工具类
 * 🙃
 *
 * @author:zhaozhenghao
 * @Email :736720794@qq.com
 * @date :2019/04/07 13:29
 * DataHandleUtils.java
 */

public class DataHandleUtils {

    private DataHandleUtils() {
    }

    /**
     * 计算正确率或百分比
     *
     * @param num
     * @param total 总数
     * @param scale 保留小数点后几位
     * @return
     */
    public static String accuracy(double num, double total, int scale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy = num / total * 100;
        return df.format(accuracy);
    }
}

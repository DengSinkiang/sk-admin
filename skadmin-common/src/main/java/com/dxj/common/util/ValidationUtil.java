package com.dxj.common.util;

import com.dxj.common.exception.BadRequestException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 *
 * @author dxj
 * @date 2019-04-23
 */
public class ValidationUtil {

    /**
     * 由字母数字下划线组成且开头必须是字母，不能超过16位
     */
    private static final Pattern pUsername = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}");

    /**
     * 手机号
     */
    private static final Pattern pMobile = Pattern.compile("^1[3458][0-9]\\d{8}$");

    /**
     * 邮箱
     */
    private static final Pattern pEmail = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");

    public static boolean Username(String v){

        Matcher m = pUsername.matcher(v);
        return m.matches();
    }

    public static boolean Mobile(String v){

        Matcher m = pMobile.matcher(v);
        return m.matches();
    }

    public static boolean Email(String v){

        Matcher m = pEmail.matcher(v);
        return m.matches();
    }
    /**
     * 验证空
     *
     * @param optional
     */
    public static void isNull(Optional optional, String entity, String parameter, Object value) {
        if (!optional.isPresent()) {
            String msg = entity
                    + " 不存在 "
                    + "{ " + parameter + ":" + value.toString() + " }";
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null) {
            return false;
        }
        String regEx = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return string.matches(regEx);
    }
}

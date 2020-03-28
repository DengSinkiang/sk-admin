package com.dxj.util;

import com.dxj.exception.SkException;
import org.hibernate.exception.ConstraintViolationException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具 2019-01-06
 * @author Sinkiang
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    public static void throwForeignKeyException(Throwable e, String msg){
        Throwable t = e.getCause();
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause();
        }
        if (t != null) {
            throw new SkException(msg);
        }
        assert false;
        throw new SkException("删除失败：" + t.getMessage());
    }
}

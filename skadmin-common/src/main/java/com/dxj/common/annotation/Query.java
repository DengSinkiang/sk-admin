package com.dxj.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dxj
 * @date 2019-6-4 13:52:30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /** Dong ZhaoYang 2017/8/7 基本对象的属性名 */
    String propName() default "";
    /** Dong ZhaoYang 2017/8/7 查询方式 */
    Type type() default Type.EQUAL;

    /**
     * 连接查询的属性名，如User类中的dept
     * @return
     */
    String joinName() default "";

    /**
     * 默认左连接
     * @return
     */
    Join join() default Join.LEFT;

    enum Type {
        EQUAL
        // 大于等于
        , GREATER_THAN_EQUAL
        // 小于等于
        , LESS_THAN_EQUAL
        // 中模糊查询
        , LIKE
        // 小于
        , LESS_THAN
        // 大于
        , GREATER_THAN
        // 在之内
        , IN
    }

    /**
     * @author Zheng Jie
     * 适用于简单连接查询，复杂的请自定义该注解，或者使用sql查询
     */
    enum Join {
        // jie 2019-6-4 13:18:30 左连接
        LEFT
        // jie 2019-6-4 13:18:30 右连接
        , RIGHT
    }

}


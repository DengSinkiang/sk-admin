package com.dxj.annotation;

import com.dxj.annotation.type.LogActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sinkiang
 * @date 2018-11-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	String value() default "";

	/**
	 * 是否启用
	 *
	 * @return
	 */
	boolean enable() default true;

	LogActionType type() default LogActionType.SELECT;
}


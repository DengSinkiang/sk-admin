package com.dxj.exception;

/**
 * @author dxj
 * @date 2018-11-23
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Class clazz, Object... saveBodyParamsMap) {
        super(EntityException.generateMessage(clazz.getSimpleName(), "已存在", EntityException.toMap(String.class, String.class, saveBodyParamsMap)));
    }
}

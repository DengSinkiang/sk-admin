package com.dxj.exception;

/**
 * @author dxj
 * @date 2018-11-23
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, Object... saveBodyParamsMap) {
        super(EntityException.generateMessage(clazz.getSimpleName(), "不存在", EntityException.toMap(String.class, String.class, saveBodyParamsMap)));
    }

}

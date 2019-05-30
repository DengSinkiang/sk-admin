package com.dxj.exception;

/**
 * @author dxj
 * @date 2019-04-23
 */
public class EntityNotFoundException extends RuntimeException {

<<<<<<< HEAD
    public EntityNotFoundException(Class clazz, Object... searchParamsMap) {
        super(EntityException.generateMessage(clazz.getSimpleName(), "不存在", EntityException.toMap(String.class, String.class, searchParamsMap)));
=======
    public EntityNotFoundException(Class clazz, Object... saveBodyParamsMap) {
        super(EntityException.generateMessage(clazz.getSimpleName(), "不存在", EntityException.toMap(String.class, String.class, saveBodyParamsMap)));
>>>>>>> dev
    }

}

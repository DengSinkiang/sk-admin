package com.dxj.exception;

import org.springframework.util.StringUtils;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Object o, String field, String val) {
        super(EntityExistException.generateMessage(o.getClass().getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " existed";
    }
}

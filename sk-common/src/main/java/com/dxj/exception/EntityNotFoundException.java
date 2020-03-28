package com.dxj.exception;

import org.springframework.util.StringUtils;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Object o, String field, String val) {
        super(EntityNotFoundException.generateMessage(o.getClass().getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " does not exist";
    }
}

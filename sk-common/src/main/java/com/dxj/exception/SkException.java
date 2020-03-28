package com.dxj.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Sinkiang
 * @date 2018-11-23
 * 统一异常处理
 */
@Getter
public class SkException extends RuntimeException {

    private Integer status = 400;

    public SkException(String msg) {
        super(msg);
    }

    public SkException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}


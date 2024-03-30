package com.banana.common;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private String message;

    private Integer code;

    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BusinessException(BusinessExceptionEnum e) {
        this.message = e.getMessage();
        this.code = e.getCode();
    }
}

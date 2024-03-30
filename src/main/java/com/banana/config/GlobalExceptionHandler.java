package com.banana.config;

import com.banana.common.BusinessException;
import com.banana.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public <T> Result<T> onBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("【业务异常】请求地址：{} 异常信息：{}",request,e.getMessage());
        return new Result<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler
    public <T> Result<T> onException(Exception e, HttpServletRequest request) {
        log.info("【系统异常】请求地址：{} 异常信息：{}",request,e.getMessage());
        return new Result<>(2000, "系统繁忙，请稍后再试！", null);
    }
}

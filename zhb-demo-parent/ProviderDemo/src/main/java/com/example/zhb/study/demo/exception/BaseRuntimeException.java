package com.example.zhb.study.demo.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常
 * @Author: zhouhb
 * @date: 2021/12/31/17:42
 * @Description:
 */
@Slf4j
public class BaseRuntimeException extends RuntimeException{

    private Integer code = 500;

    private String msg = "服务异常";

    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

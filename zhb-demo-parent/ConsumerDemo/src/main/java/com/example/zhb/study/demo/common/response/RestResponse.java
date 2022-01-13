package com.example.zhb.study.demo.common.response;

import lombok.Data;

/**
 * 封装基本的返回接口
 * @Author: zhouhb
 * @date: 2022/1/11/16:35
 * @Description:
 */
@Data
public class RestResponse<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回内容
     */
    private String msg;
    /**
     * 返回实体
     */
    private T data;
    /**
     * dubbo日志跟踪链
     */
    private String traceId;

    public RestResponse() {
    }

    public RestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RestResponse<T> ok(T data) {
        return new RestResponse<>(200, "ok", data);
    }

    public static <T> RestResponse<T> error(T data) {
        return new RestResponse<>(500, "error", data);
    }
}

package com.example.zhb.study.demo.day7;

/**
 * 事件code 枚举值
 * @Author: zhouhb
 * @date: 2021/10/18/18:02
 * @Description:
 */
public enum EventCodeEnum {
    ONE("ONE","绑定到A1ServiceImpl处理上"),
    TWO("TWO","绑定到A2ServiceImpl处理上"),
    THREE("THREE","绑定到B1ServiceImpl处理上"),
    FOUR("FOUR","绑定到B2ServiceImpl处理上");

    private String code;

    private String msg;

    EventCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

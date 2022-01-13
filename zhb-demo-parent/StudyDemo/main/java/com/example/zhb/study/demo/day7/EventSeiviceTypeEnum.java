package com.example.zhb.study.demo.day7;

/**
 * 事件code 枚举值
 * @Author: zhouhb
 * @date: 2021/10/18/18:02
 * @Description:
 */
public enum EventSeiviceTypeEnum {
    INTERFACEA("INTERFACEA","交给A接口的实现类去处理"),
    INTERFACEB("INTERFACEB","交给B接口的实现类去处理"),
    ;

    private String code;

    private String msg;

    EventSeiviceTypeEnum(String code, String msg) {
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

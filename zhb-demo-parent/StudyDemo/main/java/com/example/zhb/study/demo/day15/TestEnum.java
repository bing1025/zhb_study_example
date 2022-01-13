package com.example.zhb.study.demo.day15;

/**
 * @Author: zhouhb
 * @date: 2021/11/18/17:47
 * @Description:
 */
public enum TestEnum {

    STEP1("step1","第一步要做的事情",""),
    STEP2("step2","第二步要做的事情",""),
    STEP3("step3","第三步要做的事情","")
    ;


    TestEnum(String actCode, String actDesc, String actType) {
        this.actCode = actCode;
        this.actDesc = actDesc;
        this.actType = actType;
    }

    /**
     * 页面步骤事件编码
     */
    private String actCode;

    /**
     * 页面步骤事件描述
     */
    private String actDesc;

    /**
     * 页面步骤类型
     * 目前没用，以后扩展
     */
    private String actType;

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getActCode() {
        return actCode;
    }

    public String getActDesc() {
        return actDesc;
    }

    public String getActType() {
        return actType;
    }
}

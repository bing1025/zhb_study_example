package com.example.zhb.study.demo.day3.initializingBeanDemo;

/**
 * 运用枚举值来列举接口的实现类，方便选择性调用
 * 方法2：通过spring的ioc容器管理寻找对象，className是实现类的简称，例如 @Service("serviceTwo") 定义的
 * @Author: zhouhb
 * @date: 2021/09/24/10:57
 * @Description:
 */
public enum ServiceEnum2 {
    ONE("ONE","serviceOne"),
    TWO("TWO","serviceTwo");

    private String version;
    private String className;

    ServiceEnum2(String version, String className) {
        this.version = version;
        this.className = className;
    }

    public String getVersion() {
        return version;
    }

    public String getClassName() {
        return className;
    }
}

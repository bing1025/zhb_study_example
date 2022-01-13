package com.example.zhb.study.demo.day3.initializingBeanDemo;

/**
 * 运用枚举值来列举接口的实现类，方便选择性调用
 * 方法3：直接类的全路径，通过反射实现
 * @Author: zhouhb
 * @date: 2021/09/24/10:57
 * @Description:
 */
public enum ServiceEnum3 {
    ONE("ONE","com.example.zhb.study.demo.day3.initializingBeanDemo.ServiceOne"),
    TWO("TWO","com.example.zhb.study.demo.day3.initializingBeanDemo.ServiceTwo");

    private String version;
    private String classPath;

    ServiceEnum3(String version, String classPath) {
        this.version = version;
        this.classPath = classPath;
    }

    public String getVersion() {
        return version;
    }

    public String getClassPath() {
        return classPath;
    }
}

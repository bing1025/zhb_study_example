package com.example.zhb.study.demo.day3.initializingBeanDemo;

/**
 * 运用枚举值来列举接口的实现类，方便选择性调用
 * 方法1：接口的实现类直接指定的方式，限制一定要为接口的 实现类
 * @Author: zhouhb
 * @date: 2021/09/24/10:57
 * @Description:
 */
public enum ServiceEnum1 {
    ONE("ONE",ServiceOne.class),
    TWO("TWO",ServiceTwo.class);

    private String version;
    private Class<? extends ServiceInterface> interfaceService;

    ServiceEnum1(String version, Class<? extends ServiceInterface> interfaceService) {
        this.version = version;
        this.interfaceService = interfaceService;
    }

    public String getVersion() {
        return version;
    }

    public Class<? extends ServiceInterface> getInterfaceService() {
        return interfaceService;
    }
}

package com.example.zhb.study.demo.day2.spring;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: zhouhb
 * @date: 2021/06/25/20:01
 * @Description:
 */
@Data
@Slf4j
public class Entitlement {

    private String name;

    private int age;

    public Entitlement() {
        log.info("--------------zhb--------------");
    }

    @PostConstruct
    public void init(){
        System.out.println("bean的初始化");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("bean的销毁");
    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Entitlement entitlement = Entitlement.class.newInstance();
        System.out.println("entitlement = " + entitlement);
    }
}

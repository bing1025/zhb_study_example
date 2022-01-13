package com.example.zhb.study.demo.day2.spring;

import com.example.zhb.study.demo.day2.spring.beanPostProcessor.HelloService;
import com.example.zhb.study.demo.day2.spring.beanPostProcessor.RountingInjected;
import org.springframework.stereotype.Service;

/**
 * @Author: zhouhb
 * @date: 2021/06/25/19:56
 * @Description:
 */
@Service
public class ServiceB {

    @RountingInjected(value = "helloServiceImpl1")
    private HelloService helloService;

    public void testSayHello() {
        helloService.sayHello();
    }
}

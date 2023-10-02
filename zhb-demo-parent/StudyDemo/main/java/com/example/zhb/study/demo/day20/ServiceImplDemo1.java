package com.example.zhb.study.demo.day20;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: zhouhb
 * @date: 2022/02/23/20:07
 * @Description:
 */
@Service("serviceInterface")
public class ServiceImplDemo1 implements ServiceInterface{

    @Override
    public void testAutowired(){
        System.out.println("===test1===");
        test();
    }

    public void test(){
        System.out.println("===test2===");
    }
}

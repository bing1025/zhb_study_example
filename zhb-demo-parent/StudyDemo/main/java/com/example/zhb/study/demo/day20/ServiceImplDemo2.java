package com.example.zhb.study.demo.day20;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: zhouhb
 * @date: 2022/02/23/20:07
 * @Description:
 */
@Component
@Primary
public class ServiceImplDemo2 extends ServiceImplDemo1{


    @Override
    public void test(){
        System.out.println("===test22222===");
    }
}

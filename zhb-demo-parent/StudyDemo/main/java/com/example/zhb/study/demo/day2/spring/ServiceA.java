package com.example.zhb.study.demo.day2.spring;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @Author: zhouhb
 * @date: 2021/06/25/19:56
 * @Description:
 */
@Service
@Data
public class ServiceA {
    private String name;
    private String address;

    public ServiceA() {
    }

    public ServiceA(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ServiceA getServiceA(){
        return new ServiceA("zhb--create","啦啦啦");
    }



}

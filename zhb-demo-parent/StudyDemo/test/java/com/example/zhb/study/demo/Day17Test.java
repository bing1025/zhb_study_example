package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day16.EntityHelper;
import com.example.zhb.study.demo.day16.EntityHelperExtend;
import com.example.zhb.study.demo.day20.CompentDemo1;
import com.example.zhb.study.demo.day20.CompentDemo2;
import com.example.zhb.study.demo.day20.ServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 事件监听器的使用方法
 * @Author: zhouhb
 * @date: 2021/10/18/18:53
 * @Description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day20")
public class Day17Test {

    @Autowired
    private CompentDemo1 compentDemo1;

    @Autowired
    private CompentDemo2 compentDemo2;

    @Autowired
    private ServiceInterface serviceInterface;

    @Test
    public void test(){
        compentDemo1.testAutowired();
        System.out.println("===========");
        compentDemo2.testAutowired();
        System.out.println("===========");
        serviceInterface.testAutowired();
    }
}

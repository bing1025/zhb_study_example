package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day3.initializingBeanDemo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 实例化不同实现类的用法测试
 * @Author: zhouhb
 * @date: 2021/09/24/11:50
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
public class InitializingBeanDemoTest {

    @Autowired
    ServiceFactory1 serviceFactory1;

    @Autowired
    ServiceFactory2 serviceFactory2;

    @Autowired
    ServiceFactory3 serviceFactory3;

    @Autowired
    ServiceFactory4 serviceFactory4;

    @Autowired
    ServiceFactory5 serviceFactory5;

    @Test
    public void test1(){
        ServiceInterface one = serviceFactory1.getServiceInterfaceByVersion("one-version");
        one.test();
        ServiceInterface two = serviceFactory1.getServiceInterfaceByVersion("two-version");
        two.test();

        System.out.println("=====");

        ServiceInterface one2 = serviceFactory2.getServiceInterfaceByVersion("serviceOne");
        one2.test();
        ServiceInterface two2 = serviceFactory2.getServiceInterfaceByVersion("serviceTwo");
        two2.test();

        System.out.println("=====");

        ServiceInterface one3 = serviceFactory3.getServiceInterfaceByVersion("serviceOne");
        one3.test();
        ServiceInterface two3 = serviceFactory3.getServiceInterfaceByVersion("serviceTwo");
        two3.test();

        System.out.println("=====");

        ServiceInterface one4 = serviceFactory4.getServiceInterfaceByVersion("ONE");
        one4.test();
        ServiceInterface two4 = serviceFactory4.getServiceInterfaceByVersion("TWO");
        two4.test();

        System.out.println("=====");

        ServiceInterface one5 = serviceFactory5.getServiceInterfaceByVersion("ONE");
        one5.test();
        ServiceInterface two5 = serviceFactory5.getServiceInterfaceByVersion("TWO");
        two5.test();

        System.out.println("=====");


    }

    @Test
    public void test2(){
        System.out.println("=====");
    }

}

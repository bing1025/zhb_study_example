package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day9.enhance.initializingBean.ServiceOne;
import com.example.zhb.study.demo.day9.enhance.initializingBean.ServiceTwo;
import com.example.zhb.study.demo.day9.test.mock.OutInterface;
import com.example.zhb.study.demo.day9.test.replace.OldService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * 事件监听器的使用方法
 * @Author: zhouhb
 * @date: 2021/10/18/18:53
 * @Description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day9")
public class Day9Test {

    @Reference
    private OutInterface OutInterface;

    @Autowired
    private OldService oldService;

    @Autowired
    private ServiceOne serviceOne;


    @Autowired
    List<ServiceTwo> serviceTwos;

    @Autowired
    List<ServiceOne> serviceOnes;



    @Test
    public void testInterfaceMock(){

        // todo dubbo的等到搭建了一个服务生产者，消费者再来 mock测试验证
        OutInterface.work();
    }

    @Test
    public void testReplace(){
        oldService.test();
        oldService.test1();
    }

    @Test
    public void testPrimary(){
        serviceOne.test();
        serviceOne.version();
    }

    @Test
    public void testInitializingBean(){

        for (ServiceOne serviceOne : serviceOnes) {
            System.out.println("====="+serviceOne.getClass().getName());
            serviceOne.test();
        }

        for (ServiceTwo serviceTwo : serviceTwos) {
            System.out.println("====="+serviceTwo.getClass().getName());
            serviceTwo.test();
        }
    }
}

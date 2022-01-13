package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day7.EventCodeEnum;
import com.example.zhb.study.demo.day7.EventSeiviceTypeEnum;
import com.example.zhb.study.demo.day7.MyApplicationEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day7")
public class MyApplicationListenerTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test1(){
        //发布MyApplicationEvent事件 : 这个触发的动作可以交给 定时任务完成，也可以交给MQ完成，以此来解耦实现
        applicationContext.publishEvent(
                new MyApplicationEvent(this, EventSeiviceTypeEnum.INTERFACEA.getCode(), EventCodeEnum.ONE.getCode()));

        System.out.println("========");
        applicationContext.publishEvent(
                new MyApplicationEvent(this, EventSeiviceTypeEnum.INTERFACEA.getCode(), EventCodeEnum.TWO.getCode()));

        System.out.println("========");
        applicationContext.publishEvent(
                new MyApplicationEvent(this, EventSeiviceTypeEnum.INTERFACEB.getCode(), EventCodeEnum.THREE.getCode()));

        System.out.println("========");
        applicationContext.publishEvent(
                new MyApplicationEvent(this, EventSeiviceTypeEnum.INTERFACEB.getCode(), EventCodeEnum.FOUR.getCode()));
    }
}

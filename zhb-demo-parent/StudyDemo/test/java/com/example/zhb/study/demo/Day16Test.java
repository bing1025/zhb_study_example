package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day16.EntityHelper;
import com.example.zhb.study.demo.day16.EntityHelperExtend;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day16")
public class Day16Test {

    @Test
    public void test(){
        System.out.println(EntityHelper.getMsg());
        System.out.println(EntityHelperExtend.getMsg());

        EntityHelper entityHelper = new EntityHelper();
        System.out.println(entityHelper.getMsg2());

        EntityHelperExtend entityHelperExtend = new EntityHelperExtend();
        System.out.println(entityHelperExtend.getMsg3());

    }
}

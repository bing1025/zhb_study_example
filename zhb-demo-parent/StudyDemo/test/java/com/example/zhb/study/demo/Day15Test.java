package com.example.zhb.study.demo;


import com.example.zhb.study.demo.day15.TestExtEnum;
import com.example.zhb.study.demo.day15.initEnum.EnumInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.example.zhb.study.demo.day15.TestEnum;

/**
 * 事件监听器的使用方法
 * @Author: zhouhb
 * @date: 2021/10/18/18:53
 * @Description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day15")
public class Day15Test {

    static Class[] classes ={
            TestExtEnum.class
    };

    @Test
    public void testExtEnum1(){
        for (TestEnum value : TestEnum.values()) {
            System.out.println("===="+value.getActCode());
        }
    }

    @Test
    public void testExtEnum2(){
        EnumInitializer.initEnums(classes);
        for (TestEnum value : TestEnum.values()) {
            System.out.println("===="+value.getActCode());
        }
    }
}

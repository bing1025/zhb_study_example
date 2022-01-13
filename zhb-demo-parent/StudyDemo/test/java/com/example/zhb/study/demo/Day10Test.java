package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day10.handler.TaskExecutor;
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
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day10")
public class Day10Test {

    @Test
    public void test(){
        TaskExecutor taskExecutor = new TaskExecutor();

        System.out.println(taskExecutor.execute("Saved"));
        System.out.println(taskExecutor.execute("Changed"));
        System.out.println(taskExecutor.execute("Deleted"));
    }
}

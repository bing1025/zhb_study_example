package com.example.zhb.study.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.day8.EntityImplement;
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
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day8")
public class EntityImplementTest {

    @Test
    public void test1(){
        EntityImplement entityImplement = new EntityImplement();
        entityImplement.setRemarkExt("zhb hello");
        entityImplement.setMsgExt("zhb world");
        System.out.println("========"+ JSONObject.toJSONString(entityImplement));
    }
}

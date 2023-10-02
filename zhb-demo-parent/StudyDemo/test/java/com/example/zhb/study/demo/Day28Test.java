package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day20.CompentDemo1;
import com.example.zhb.study.demo.day20.CompentDemo2;
import com.example.zhb.study.demo.day20.ServiceInterface;
import com.example.zhb.study.demo.day28.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

/**
 * 事件监听器的使用方法
 * @Author: zhouhb
 * @date: 2021/10/18/18:53
 * @Description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day28")
public class Day28Test {


    @Test
    public void test(){
        ChildDTOA childDTOA = new ChildDTOA();
        childDTOA.setSchool("schoolA");
        childDTOA.setName("nameA");
        AnnotationHandle.handleAnnotation("zhbA",childDTOA);
        System.out.println("===========");
        ChildDTOB childDTOB = new ChildDTOB();
        childDTOB.setCity("cityB");
        childDTOB.setName("nameB");
        AnnotationHandle.handleAnnotation("zhbB",childDTOB);
        System.out.println("===========");
        ChildDTOC childDTOC = new ChildDTOC();
        childDTOC.setHome("homeC");
        childDTOC.setName("nameC");
        AnnotationHandle.handleAnnotation("zhbC",childDTOC);
        System.out.println("===========");

    }
}

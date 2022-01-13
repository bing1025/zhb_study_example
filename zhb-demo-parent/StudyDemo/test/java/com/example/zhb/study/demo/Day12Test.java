package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day12.ext.enums.ActEnum;
import com.example.zhb.study.demo.day12.ext.enums.NodeEnum;
import com.example.zhb.study.demo.day12.ext.mq.Customer;
import com.example.zhb.study.demo.day12.simple.event.EmailEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * 事件监听器的使用方法
 * @Author: zhouhb
 * @date: 2021/10/18/18:53
 * @Description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)  //这里的Application是springboot的启动类名
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.day12")
public class Day12Test {

    @Autowired
    private WebApplicationContext webapplicationcontext;

    /**
     * 普通用法
     */
    @Test
    public void test(){
        EmailEvent emailEvent = new EmailEvent("object", "172572575@qq.com", "zhb###listener");
        webapplicationcontext.publishEvent(emailEvent);
    }

    /**
     * 基于这个原理：由此扩展出来一些复杂的场景
     * 例如： 消息代办提醒之类的，或者 流程中心，流程引擎触发之类的
     */
    @Test
    public void test2(){
        Customer customer = new Customer();

        System.out.println("==============开始第一个节点，触发节点感应，这个是自动触发的，后面的都是页面业务触发");
        customer.startNode(NodeEnum.NODE1.getNodeCode());
        System.out.println("==============节点一触发了，处理节点一的业务了");
        customer.startPageAct(ActEnum.STEP1.getActCode());
        System.out.println("==============节点一的业务处理完了，去通知开启节点二了");
        customer.startNode(NodeEnum.NODE2.getNodeCode());
        System.out.println("==============节点二触发了，处理节点二的业务了");
        customer.startPageAct(ActEnum.STEP2.getActCode());
        System.out.println("==============节点二的业务处理完了，去通知开启节点三了");
        customer.startNode(NodeEnum.NODE3.getNodeCode());
        System.out.println("==============节点三触发了，处理节点三的业务了");
        customer.startPageAct(ActEnum.STEP3.getActCode());

    }

}

package com.example.zhb.study.demo.day20;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2022/02/23/20:07
 * @Description:
 */
@Component
public class CompentDemo1 {

    public void testAutowired(){
        System.out.println("===test1===");
        test();
    }

    public void test(){
        System.out.println("===test2===");
    }
}

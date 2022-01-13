package com.example.zhb.study.demo.day9.test.replace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 老的Service实现
 * @Author: zhouhb
 * @date: 2021/10/26/13:51
 * @Description:
 */
@Slf4j
@Service("oldService1")
public class OldService implements InnerInterface{

    public String test(){
        log.debug("这是老的Service的返回");
        return "这是老的Service的返回";
    }

    @Override
    public String test1() {
        log.debug("这是老的Service的test1()方法返回");
        return "这是老的Service的test1()方法返回";
    }

}

package com.example.zhb.study.demo.day7.impl;

import com.example.zhb.study.demo.day7.EventCodeEnum;
import com.example.zhb.study.demo.day7.InterfaceB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * B接口的实现类B1
 * @Author: zhouhb
 * @date: 2021/10/18/17:58
 * @Description:
 */
@Slf4j
@Service("b1ServiceImpl")
public class B1ServiceImpl implements InterfaceB {
    @Override
    public boolean containsEventCode(String eventCode) {
        return EventCodeEnum.THREE.getCode().equals(eventCode);
    }

    @Override
    public String doEventB() {
        log.info("这个是 B1ServiceImpl 在响应处理。。。");
        return "这个是 B1ServiceImpl 在响应处理。。。";
    }
}

package com.example.zhb.study.demo.day7.impl;

import com.example.zhb.study.demo.day7.EventCodeEnum;
import com.example.zhb.study.demo.day7.InterfaceA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * A接口的实现类A2
 * @Author: zhouhb
 * @date: 2021/10/18/17:58
 * @Description:
 */
@Slf4j
@Service("a2ServiceImpl")
public class A2ServiceImpl implements InterfaceA {
    @Override
    public boolean containsEventCode(String eventCode) {
        return EventCodeEnum.TWO.getCode().equals(eventCode);
    }

    @Override
    public String doEventA() {
        log.info("这个是 A2ServiceImpl 在响应处理。。。");
        return "这个是 A2ServiceImpl 在响应处理。。。";
    }
}

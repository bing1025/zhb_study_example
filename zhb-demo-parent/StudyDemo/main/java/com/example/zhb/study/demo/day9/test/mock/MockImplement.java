package com.example.zhb.study.demo.day9.test.mock;

import com.example.zhb.study.demo.day9.enhance.anno.DubboMock;
import lombok.extern.slf4j.Slf4j;

/**
 * 外部接口，被mock代理
 * @Author: zhouhb
 * @date: 2021/10/26/9:29
 * @Description:
 */
@Slf4j
@DubboMock
public class MockImplement implements OutInterface{
    @Override
    public String work() {
        log.debug("接口已经被mock,直接调用默认实现");
        return "接口已经被mock,直接调用默认实现";
    }
}

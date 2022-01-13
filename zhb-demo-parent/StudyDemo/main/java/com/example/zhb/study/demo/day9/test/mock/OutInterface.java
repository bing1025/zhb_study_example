package com.example.zhb.study.demo.day9.test.mock;

/**
 * 调用的线上或者外部服务，本地需要mock,不然访问不了没法测试
 * @Author: zhouhb
 * @date: 2021/10/26/9:26
 * @Description:
 */
public interface OutInterface {
    public String work();
}

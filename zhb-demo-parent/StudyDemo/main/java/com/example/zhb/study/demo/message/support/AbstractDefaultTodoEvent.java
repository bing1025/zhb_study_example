package com.example.zhb.study.demo.message.support;

/***
 *
 * @author Xiaojie.Li
 * @description 默认基础事件类
 * @date 2021/6/1
 */
public abstract class AbstractDefaultTodoEvent extends MessageEvent{
    public AbstractDefaultTodoEvent(MessageSource source) {
        super(source);
    }
}

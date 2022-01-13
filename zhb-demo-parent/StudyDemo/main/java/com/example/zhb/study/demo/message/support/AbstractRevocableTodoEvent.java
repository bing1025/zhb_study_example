package com.example.zhb.study.demo.message.support;

/***
 *
 * @author Xiaojie.Li
 * @description 可消退的待办事件抽象类
 * @date 2021/5/31
 */
public abstract class AbstractRevocableTodoEvent extends MessageEvent{

    volatile boolean withdraw = false;

    public AbstractRevocableTodoEvent(MessageSource source){
        super(source);
    }

    public boolean isWithdraw(){
        return withdraw;
    }

    public AbstractRevocableTodoEvent withdraw(){
        this.withdraw = true;
        return this;
    }
}

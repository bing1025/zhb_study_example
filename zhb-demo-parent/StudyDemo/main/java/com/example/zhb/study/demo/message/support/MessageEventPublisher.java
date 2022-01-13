package com.example.zhb.study.demo.message.support;

/***
 *
 * @author Xiaojie.Li
 * @description 消息事件发布器
 * @date 2021/5/31
 */
public interface MessageEventPublisher {

    boolean publishEvent(MessageEvent event);

    boolean publishEvent(MessageEvent event, boolean isSync);
}

package com.example.zhb.study.demo.message.support;

import java.util.EventListener;

/***
 *
 * @author Xiaojie.Li
 * @description 监听器
 * @date 2021/5/31
 */
public interface MessageListener extends EventListener {

    boolean doMsg(MessageEvent event);
}

package com.example.zhb.study.demo.day12.simple.listener;

import com.example.zhb.study.demo.day12.simple.event.EmailEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 邮件通知监听
 * @Author: zhouhb
 * @date: 2021/11/04/14:29
 * @Description:
 */
@Component
public class EmailListener implements ApplicationListener<EmailEvent> {

    @Override
    public void onApplicationEvent(EmailEvent emailEvent) {
        System.out.println("邮件地址：" + emailEvent.getEmail());
        System.out.println("邮件内容：" + emailEvent.getContent());
    }
}

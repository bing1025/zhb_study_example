package com.example.zhb.study.demo.day12.simple.event;

import org.springframework.context.ApplicationEvent;

/**
 * 邮件通知事件：主要用于处理邮件相关的事件
 * @Author: zhouhb
 * @date: 2021/11/04/11:21
 * @Description:
 */
public class EmailEvent extends ApplicationEvent {
    private String email;

    private String content;

    public EmailEvent(Object source){
        super(source);
    }

    public EmailEvent(Object source, String email, String content){
        super(source);
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

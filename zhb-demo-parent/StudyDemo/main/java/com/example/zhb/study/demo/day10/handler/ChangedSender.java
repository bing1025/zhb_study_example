package com.example.zhb.study.demo.day10.handler;

/**
 * 实现一个具体的任务处理器
 * 更改操作
 */
@TaskHandler(taskType = "Changed")
public class ChangedSender extends AbstractTaskHandler {

    @Override
    public String execute(String taskType) {
        return "执行的是 Changed 事件的事情";
    }

}
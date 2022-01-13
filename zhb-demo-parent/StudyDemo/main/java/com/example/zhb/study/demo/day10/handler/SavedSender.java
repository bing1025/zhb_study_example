package com.example.zhb.study.demo.day10.handler;

/**
 * 实现一个具体的任务处理器
 * 新增操作
 */
@TaskHandler(taskType = "Saved")
public class SavedSender extends AbstractTaskHandler {

    @Override
    public String execute(String taskType) {
        return "执行的是 Saved 事件的事情";
    }

}
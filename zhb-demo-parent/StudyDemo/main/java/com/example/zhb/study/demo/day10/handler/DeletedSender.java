package com.example.zhb.study.demo.day10.handler;

/**
 * 实现一个具体的任务处理器
 * 删除操作
 */
@TaskHandler(taskType = "Deleted")
public class DeletedSender extends AbstractTaskHandler {

    @Override
    public String execute(String taskType) {
        return "执行的是 Deleted 事件的事情";
    }

}
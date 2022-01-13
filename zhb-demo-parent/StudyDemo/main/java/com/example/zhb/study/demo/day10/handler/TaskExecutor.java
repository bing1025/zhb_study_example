package com.example.zhb.study.demo.day10.handler;

public class TaskExecutor{
    private static final String TASK_TYPE = "taskType";

    public String execute(String taskType) {
        if (TaskHandlerRegister.getTaskHandler(taskType) == null) {
            throw new RuntimeException("can't find taskHandler,taskType=" + taskType);
        }
        AbstractTaskHandler abstractHandler = TaskHandlerRegister.getTaskHandler(taskType);
        return abstractHandler.execute(taskType);
    }

}
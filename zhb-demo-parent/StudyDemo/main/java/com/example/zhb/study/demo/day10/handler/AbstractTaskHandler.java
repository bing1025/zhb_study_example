package com.example.zhb.study.demo.day10.handler;

public abstract class AbstractTaskHandler {
    /**
     * 任务执行器
     *
     * @param taskType 任务类型
     * @return 执行结果
     */
    public abstract String execute(String taskType);

}

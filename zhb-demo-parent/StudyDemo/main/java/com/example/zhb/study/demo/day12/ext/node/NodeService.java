package com.example.zhb.study.demo.day12.ext.node;

import com.example.zhb.study.demo.day12.ext.event.ActEvent;

/**
 * 流程引擎通知
 * @Author: zhouhb
 * @date: 2021/11/04/14:49
 * @Description:
 */
public interface NodeService {

    /**
     * 业务动作：流程引擎触发，通知业务系统
     * @return
     */
    String getNodeCode();

    /**
     * 业务开始前需要处理的逻辑，可以实现这个方法
     * @param actEvent
     */
    default void postProcessBeforeStart(ActEvent actEvent){}

    /**
     * 启动某个节点
     * 适用场景：流程引擎触发
     * @param actEvent
     */
    void startNode(ActEvent actEvent);

    /**
     * 业务开始后需要处理的逻辑，可以实现这个方法
     * @param actEvent
     */
    default void postProcessAfterStart(ActEvent actEvent){}


}

package com.example.zhb.study.demo.day12.ext.node;

import com.example.zhb.study.demo.day12.ext.enums.NodeEnum;
import com.example.zhb.study.demo.day12.ext.event.ActEvent;
import org.springframework.stereotype.Service;

/**
 * 第一个关键节点
 * @Author: zhouhb
 * @date: 2021/11/06/17:08
 * @Description:
 */
@Service
public class Node1ServiceImpl implements NodeService{
    @Override
    public String getNodeCode() {
        return NodeEnum.NODE1.getNodeCode();
    }

    @Override
    public void postProcessBeforeStart(ActEvent actEvent) {
        System.out.println("节点处理之前=="+getNodeCode());
    }

    @Override
    public void startNode(ActEvent actEvent) {
        System.out.println("开始处理节点的页面步骤=="+actEvent.getEventCode());
    }

    @Override
    public void postProcessAfterStart(ActEvent actEvent) {
        System.out.println("节点处理之前=="+getNodeCode());
    }
}

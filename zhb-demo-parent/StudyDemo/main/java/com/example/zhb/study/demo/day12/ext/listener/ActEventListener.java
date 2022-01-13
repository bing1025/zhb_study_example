package com.example.zhb.study.demo.day12.ext.listener;

import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.day12.ext.act.ActService;
import com.example.zhb.study.demo.day12.ext.constants.EventTypeConstants;
import com.example.zhb.study.demo.day12.ext.event.ActEvent;
import com.example.zhb.study.demo.day12.ext.node.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zhouhb
 * @date: 2021/11/04/14:45
 * @Description:
 */
@Slf4j
@Component
public class ActEventListener implements ApplicationListener<ActEvent> {

    /**
     * spring 的方式 收集所有的 NodeService 实现类
     */
    @Autowired(required = false)
    private List<NodeService> nodeProviders;

    /**
     * spring 的方式 收集所有的 NodeService 实现类
     */
    @Autowired(required = false)
    private List<ActService> actProviders;


    /**
     * 使用场景：流程状态提交,流程提交，流程开始
     * @param event
     */
    @Override
    public void onApplicationEvent(ActEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("ActEventListener start event={}", JSONObject.toJSONString(event));
        }

        // 页面事件//或者系统自定义事件处理
        if(EventTypeConstants.PAGE.equals(event.getEventType())){
            doPageEvent(event);
        }

        // 流程引擎通知事件 监听处理
        if(EventTypeConstants.PROCESS.equals(event.getEventType())){
            doProcessEvent(event);
        }

        if (log.isDebugEnabled()) {
            log.debug("ActEventListener end event={}", JSONObject.toJSONString(event));
        }
    }


    private void doPageEvent(ActEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("doPageEvent start event={}", JSONObject.toJSONString(event));
        }
        if(CollectionUtils.isEmpty(actProviders)){
            return;
        }
        for (ActService actProvider : actProviders) {
            // 遍历所有的 ActService 实现类，找到符合处理的实现类开始处理
            if(actProvider.containsEvent(event.getEventCode())){
                log.info("ActService impl {}",actProvider.getClass().getSimpleName());
                if(actProvider.isAccept(event)){
                    // 事件前触发
                    actProvider.postProcessBeforeSubmit(event);
                    // 更新环节状态
                    Integer integer = actProvider.updateProcessLinkStatus(event);
                    // 事件后触发
                    actProvider.postProcessAfterSubmit(event);
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("doPageEvent end event={}", JSONObject.toJSONString(event));
        }
    }

    private void doProcessEvent(ActEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("doProcessEvent start event={}", JSONObject.toJSONString(event));
        }
        if(CollectionUtils.isEmpty(nodeProviders)){
            return;
        }
        for (NodeService nodeProvider : nodeProviders) {
            // 遍历所有的 NodeService 实现类，找到符合处理的实现类开始处理
            if(nodeProvider.getNodeCode().equals(event.getEventCode())){
                log.info("NodeService impl {}",nodeProvider.getClass().getSimpleName());
                // 节点前触发
                nodeProvider.postProcessBeforeStart(event);
                // 启动节点
                nodeProvider.startNode(event);
                // 节点后触发
                nodeProvider.postProcessAfterStart(event);

            }
        }
        if (log.isDebugEnabled()) {
            log.debug("doProcessEvent end event={}", JSONObject.toJSONString(event));
        }
    }
}

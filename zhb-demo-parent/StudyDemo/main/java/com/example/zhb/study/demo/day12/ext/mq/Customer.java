package com.example.zhb.study.demo.day12.ext.mq;

import com.example.zhb.study.demo.day12.ext.constants.EventTypeConstants;
import com.example.zhb.study.demo.day12.ext.event.ActEvent;
import com.example.zhb.study.demo.day12.ext.handler.NodeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟接受流程引擎发送过来的mq消息，
 * * 整个事件就是 流程引擎通过mq发消息触发到下一个节点
 *  * 下一个节点就会处理这个节点的页面的业务事件，在页面处理业务事件完成的时候，就会在给流程引擎发送一条通知，告诉他我当前节点的事情做完了
 *  * 收到当前节点完成的事情后，流程引擎再往下下一个节点发送mq消息
 *  * 循环往复，完成了流程推进和页面业务响应的场景
 * 用来消费处理具体的页面事件
 * @Author: zhouhb
 * @date: 2021/11/06/17:41
 * @Description:
 */
@Slf4j
public class Customer {

    /**
     * 开启某一个节点:流程引擎通过Mq发放的方式
     * @param nodeCode
     */
    public void startNode(String nodeCode){
        log.info("startNode === "+nodeCode);

        //这里的事件类型eventType 全部是 流程引擎主动下放mq触发节点的类型
        ActEvent actEvent = new ActEvent("zhb1", nodeCode, EventTypeConstants.PROCESS);
        NodeHandler.startNodeProcessEvent(actEvent);
    }

    /**
     * 处理某一个页面事件，这个可以不用 mq触发，是在业务的代码处理完后，自己手动调用的
     * @param actCode
     */
    public void startPageAct(String actCode){
        log.info("startPageAct === "+actCode);

        //这里的事件类型eventType 全部是 页面事件
        ActEvent actEvent = new ActEvent("zhb1", actCode, EventTypeConstants.PAGE);
        NodeHandler.startNodeProcessEvent(actEvent);
    }

}

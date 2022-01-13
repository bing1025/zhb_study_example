package com.example.zhb.study.demo.day12.ext.act;

import com.example.zhb.study.demo.day12.ext.event.ActEvent;

/**
 * 模拟页面点击触发或者系统自动触发事件服务
 * @Author: zhouhb
 * @date: 2021/11/04/16:12
 * @Description:
 */
public interface ActService {

    /**
     * 判断业务实现类的事件编码是否包含引擎、流程中心通过MQ下发的事件编码
     * 如果包含，就执行该业务的实现类
     * 业务动作：可以是前端页面操作触发，也可以是系统内部自动触发
     * @param eventCode  流程中心事件编码
     * @return
     */
    boolean containsEvent(String eventCode);


    /**
     * 状态形事件：实现此方法更新状态值
     * @param actEvent
     * @return
     */
    default Integer updateProcessLinkStatus(ActEvent actEvent){ return null;}

    /**
     * 事件提交前置处理器
     * @param actEvent
     */
    default void postProcessBeforeSubmit(ActEvent actEvent){}


    /**
     * 事件提交后置处理器
     * @param actEvent
     */
    default void postProcessAfterSubmit(ActEvent actEvent){}

    /**
     * 是否达到提交节点的条件
     * true 已达到
     * false 暂未满足条件，还不能发起提交节点
     * @param actEvent
     * @return
     */
    default boolean isAccept(ActEvent actEvent){return true;}
}

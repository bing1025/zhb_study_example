package com.example.zhb.study.demo.day12.ext.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.day12.ext.event.ActEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

/**
 * 节点触发事件
 * @Author: zhouhb
 * @date: 2021/11/06/18:08
 * @Description:
 */
@Slf4j
@Component
public class NodeHandler {


    @Autowired
    private WebApplicationContext webapplicationcontext;

    private static NodeHandler nodeHandler;

    @PostConstruct
    public void init(){
        nodeHandler = this;
    }


    public static void startNodeProcessEvent(ActEvent actEvent){
        nodeHandler.doNodeProcessEvent(actEvent);
    }


    public void doNodeProcessEvent(ActEvent actEvent){
        log.info("---------doNodeProcessEvent 发布事件，actEvent={}", JSONObject.toJSONString(actEvent));
        if(webapplicationcontext.getParent() != null){
            webapplicationcontext.getParent().publishEvent(actEvent);
        }else{
            webapplicationcontext.publishEvent(actEvent);
        }
    }
}

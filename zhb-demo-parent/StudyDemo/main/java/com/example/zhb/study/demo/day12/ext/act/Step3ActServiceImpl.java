package com.example.zhb.study.demo.day12.ext.act;

import com.example.zhb.study.demo.day12.ext.enums.ActEnum;
import com.example.zhb.study.demo.day12.ext.event.ActEvent;
import org.springframework.stereotype.Service;

/**
 * 页面触发：步骤三
 * @Author: zhouhb
 * @date: 2021/11/06/14:56
 * @Description:
 */
@Service
public class Step3ActServiceImpl implements ActService{
    @Override
    public boolean containsEvent(String eventCode) {
        return ActEnum.STEP3.getActCode().equals(eventCode);
    }

    @Override
    public Integer updateProcessLinkStatus(ActEvent actEvent) {
        System.out.println("更新了数据=="+actEvent.getEventCode());
        return null;
    }

    @Override
    public void postProcessBeforeSubmit(ActEvent actEvent) {
        System.out.println(actEvent.getEventCode()+"=====之前操作");
    }

    @Override
    public void postProcessAfterSubmit(ActEvent actEvent) {
        System.out.println(actEvent.getEventCode()+"=====之后操作");
    }

    @Override
    public boolean isAccept(ActEvent actEvent) {
        return ActEnum.STEP3.getActCode().equals(actEvent.getEventCode());
    }
}

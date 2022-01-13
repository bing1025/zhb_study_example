package com.example.zhb.study.demo.day7;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * MyApplicationEvent ： 发布一个事件,其实主要是给 专门的自定义的 ApplicationListener用的，说明这个监听器只处理这一种类型的业务逻辑
 * ApplicationEvent和ApplicationListener一般都是配合使用
 * @Author: zhouhb
 * @date: 2021/10/18/17:38
 * @Description:
 */
@Getter
public class MyApplicationEvent extends ApplicationEvent {

    // 服务code，用来区分调用到的是哪一个服务
    private String serviceCode;

    // 事件code，用来区分调用到的具体实现类
    private String eventCode;

    public MyApplicationEvent(Object source, String serviceCode, String eventCode) {
        super(source);
        this.serviceCode = serviceCode;
        this.eventCode = eventCode;
    }
}

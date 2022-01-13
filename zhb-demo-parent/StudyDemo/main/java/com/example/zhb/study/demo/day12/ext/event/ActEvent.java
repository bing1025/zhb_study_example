package com.example.zhb.study.demo.day12.ext.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 动作事件包装类
 * @Author: zhouhb
 * @date: 2021/11/04/11:21
 * @Description:
 */
@Getter
@Setter
public class ActEvent extends ApplicationEvent {

    // 此值不能为空，全局唯一
    private String uuid;

    //事件编码
    private String eventCode;

    //事件类型
    private String eventType;

    // 附加参数 -- 此参数可以不在流程中传递
    private Map<String,Object> params = new HashMap<>();

    public ActEvent(String uuid, String eventCode, String eventType) {
        super("zhb-demo-source");
        this.uuid = uuid;
        this.eventCode = eventCode;
        this.eventType = eventType;
    }

    public ActEvent(String uuid, String eventCode, String eventType, Map<String, Object> params) {
        super("zhb-demo-source");
        this.uuid = uuid;
        this.eventCode = eventCode;
        this.eventType = eventType;
        this.params = params;
    }

    @Override
    public String toString() {
        return "ActEvent{" +
                "uuid='" + uuid + '\'' +
                ", eventCode='" + eventCode + '\'' +
                ", eventType='" + eventType + '\'' +
                ", params=" + params +
                '}';
    }
}

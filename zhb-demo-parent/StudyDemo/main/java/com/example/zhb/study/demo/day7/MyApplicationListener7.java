package com.example.zhb.study.demo.day7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 事件监听器--MyApplicationListener
 * 一定要实现 ApplicationListener 接口的 onApplicationEvent 方法
 * 其中 ApplicationListener<MyApplicationEvent> 的 MyApplicationEvent 定义了事件类型
 * 说明这个监听器只处理 MyApplicationEvent 这个类型的事件，不指定的话，只要是 ApplicationEvent 的子类就都可以处理
 * 将listener交给spring容器托管，加上@Component注解
 *
 * @Author: zhouhb
 * @date: 2021/10/18/17:46
 * @Description:
 */
@Component
public class MyApplicationListener7 implements ApplicationListener<MyApplicationEvent> {

    /**
     * 得到所有的InterfaceA 的实现类
     * 用list方式得到
     */
    @Autowired
    private List<InterfaceA> interfaceAList;

    /**
     * 得到所有的InterfaceB 的实现类
     * 用map方式得到 就是演示多个用法而已
     */
    @Autowired
    private Map<String,InterfaceB> interfaceBMap;

    /**
     * 这个就是具体的处理回调事件类型的方法业务逻辑了，这里写复杂些，顺便演示下模板方法的使用
     * @param event
     */
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        if(EventSeiviceTypeEnum.INTERFACEA.getCode().equals(event.getServiceCode())){
            for (InterfaceA interfaceA : interfaceAList) {
                if(interfaceA.containsEventCode(event.getEventCode())){
                    interfaceA.doEventA();
                }
            }
        }else if(EventSeiviceTypeEnum.INTERFACEB.getCode().equals(event.getServiceCode())){
            for (Map.Entry<String, InterfaceB> stringInterfaceBEntry : interfaceBMap.entrySet()) {
                InterfaceB interfaceB = stringInterfaceBEntry.getValue();
                if(interfaceB.containsEventCode(event.getEventCode())){
                    interfaceB.doEventB();
                }
            }
        }
    }
}

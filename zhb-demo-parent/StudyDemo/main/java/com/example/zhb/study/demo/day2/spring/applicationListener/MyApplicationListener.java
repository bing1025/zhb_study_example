package com.example.zhb.study.demo.day2.spring.applicationListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * https://www.yht7.com/news/32850
 *
 * * 3、ApplicationListener：监听容器中发布的事件。事件驱动模型开发；
 * * public interface ApplicationListener<E extends ApplicationEvent>
 * * 监听 ApplicationEvent 及其下面的子事件；
 * *
 * * 步骤：
 * * 1）、写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）
 * * @EventListener;
 * * 原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；
 * *
 * * 2）、把监听器加入到容器；
 * * 3）、只要容器中有相关事件的发布，我们就能监听到这个事件；
 * * ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
 * * ContextClosedEvent：关闭容器会发布这个事件；
 * * 4）、发布一个事件：
 * * applicationContext.publishEvent()；
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
 
 //当容器中发布此事件以后，方法触发
 public void onApplicationEvent(ApplicationEvent event) {
  // TODO Auto-generated method stub
  System.out.println("收到事件："+event);
 }
}
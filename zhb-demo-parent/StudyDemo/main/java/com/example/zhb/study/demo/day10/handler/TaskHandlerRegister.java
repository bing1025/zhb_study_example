package com.example.zhb.study.demo.day10.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 普通Java类获取spring 容器的bean的5种方法
 * 方法一：在初始化时保存ApplicationContext对象
 * 方法二：通过Spring提供的工具类获取ApplicationContext对象
 * 方法三：继承自抽象类ApplicationObjectSupport
 * 方法四：继承自抽象类WebApplicationObjectSupport
 * 方法五：实现接口ApplicationContextAware
 */
@Component  // 加入spring容器中才能生效被发现
public class TaskHandlerRegister extends ApplicationObjectSupport {

    private final static Map<String,AbstractTaskHandler> TASK_HANDLERS_MAP = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskHandlerRegister.class);

    // 普通Java类获取spring 容器的bean的5种方法之一
    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        super.initApplicationContext(context);
        // 获得 将注解 @TaskHandler 加到 类上面的所有交给spring管理的bean
        Map taskBeanMap = context.getBeansWithAnnotation(TaskHandler.class);

        taskBeanMap.keySet().forEach(beanName -> {
            Object bean = taskBeanMap.get(beanName);
            Class clazz = bean.getClass();
            // 处理按照规范 将注解 @TaskHandler 加到 AbstractTaskHandler 子类的 bean
            if (bean instanceof AbstractTaskHandler && clazz.getAnnotation(TaskHandler.class) != null) {
                TaskHandler taskHandler = (TaskHandler) clazz.getAnnotation(TaskHandler.class);
                String taskType = taskHandler.taskType();
                if (TASK_HANDLERS_MAP.keySet().contains(taskType)) {
                    throw new RuntimeException("TaskType has Exits. TaskType=" + taskType);
                }
                TASK_HANDLERS_MAP.put(taskHandler.taskType(), (AbstractTaskHandler) taskBeanMap.get(beanName));
                LOGGER.info("Task Handler Register. taskType={},beanName={}", taskHandler.taskType(), beanName);
            }
        });
    }

    public static AbstractTaskHandler getTaskHandler(String taskType) {
        return (AbstractTaskHandler) TASK_HANDLERS_MAP.get(taskType);
    }

}
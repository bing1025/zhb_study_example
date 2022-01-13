package com.example.zhb.study.demo.day9.enhance.handler;

import com.example.zhb.study.demo.day9.enhance.component.CustomBeanDefinitionCache;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 类定义处理器接口
 * @Author: zhouhb
 * @date: 2021/10/25/17:47
 * @Description:
 */
public interface CustomBeanDefinitionHandler {

    void handle(CustomBeanDefinitionCache cache);

    default String getBeanClassName(BeanDefinition beanDefinition){
        return beanDefinition.getBeanClassName();
    }
}

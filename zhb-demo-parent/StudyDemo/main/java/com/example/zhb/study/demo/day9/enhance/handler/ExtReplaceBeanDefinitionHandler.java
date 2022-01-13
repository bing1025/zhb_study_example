package com.example.zhb.study.demo.day9.enhance.handler;

import com.example.zhb.study.demo.day9.enhance.anno.ExtReplaceBean;
import com.example.zhb.study.demo.day9.enhance.anno.MyBeanDefinitionHandler;
import com.example.zhb.study.demo.day9.enhance.component.CustomBeanDefinitionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Collection;

/**
 * 替换bean的实现逻辑
 * @Author: zhouhb
 * @date: 2021/10/25/18:05
 * @Description:
 */
@Slf4j
@MyBeanDefinitionHandler
public class ExtReplaceBeanDefinitionHandler implements CustomBeanDefinitionHandler{
    @Override
    public void handle(CustomBeanDefinitionCache cache) {
        // 获取具有替换注解的bean定义集合
        Collection<BeanDefinition> beanDefinitionsByAnnotation = cache.getBeanDefinitionsByAnnotation(ExtReplaceBean.class);

        for (BeanDefinition beanDefinition : beanDefinitionsByAnnotation) {
            Class<?> clazz;
            try {
                clazz = Class.forName(getBeanClassName(beanDefinition));
            } catch (ClassNotFoundException e) {
                continue;
            }
            ExtReplaceBean annotation = clazz.getAnnotation(ExtReplaceBean.class);
            // 进行bean定义的替换
            // classTypes 是要替换的类
            for (Class type : annotation.classTypes()) {
                cache.getBeanDefinitionsByType(type).forEach(replaceBeanDefinition ->{
                    // beanDefinition 是真实的类，replaceBeanDefinition 是要来替换掉它的类
                    cache.replace(beanDefinition,(BeanDefinition)replaceBeanDefinition);
                });
            }
            // 删除bean定义
            // deleteClasses 是要删除的类
            for (Class deleteClass : annotation.deleteClasses()) {
                cache.getBeanDefinitionsByType(deleteClass).forEach(deleteBeanDefinition ->{
                    cache.delete(cache.getBeanDefinitionId((BeanDefinition) deleteBeanDefinition));
                });
            }
        }
    }
}

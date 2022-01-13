package com.example.zhb.study.demo.day9.enhance.handler;

import com.example.zhb.study.demo.day9.enhance.anno.DubboMock;
import com.example.zhb.study.demo.day9.enhance.anno.MyBeanDefinitionHandler;
import com.example.zhb.study.demo.day9.enhance.component.CustomBeanDefinitionCache;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 替换接口实现
 * @Author: zhouhb
 * @date: 2021/10/25/18:03
 * @Description:
 */
@MyBeanDefinitionHandler
public class DubboMockBeanDefinitionHandler implements CustomBeanDefinitionHandler{

    @Override
    public void handle(CustomBeanDefinitionCache cache) {
        Collection<BeanDefinition> referenceBeanDefinitions = cache.getBeanDefinitionsByType(ReferenceBean.class);
        Collection<BeanDefinition> dubboMockBeanDefinitions = cache.getBeanDefinitionsByAnnotation(DubboMock.class);

        for (BeanDefinition beanDefinition : dubboMockBeanDefinitions) {
            String beanClassName = getBeanClassName(beanDefinition);
            Class<?> clazz;
            try {
                clazz = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                continue;
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            if(ObjectUtils.isEmpty(interfaces)){
                continue;
            }

            for (Class<?> anInterface : interfaces) {
                Collection<BeanDefinition> dubboReferences = getBeanBefinitionsByType(anInterface,referenceBeanDefinitions);

                for (BeanDefinition dubboReference : dubboReferences) {
                    if(dubboReference.getPropertyValues().contains("mock")){
                        dubboReference.getPropertyValues().removePropertyValue("mock");
                    }
                    dubboReference.getPropertyValues().addPropertyValue(new PropertyValue("mock",beanClassName));
                }
            }
            // 将该类定义从spring中删除
            cache.delete(cache.getBeanDefinitionId(beanDefinition));
        }

    }

    private Collection<BeanDefinition> getBeanBefinitionsByType(Class<?> clazz, Collection<BeanDefinition> beanDefinitionsByType) {
        List<BeanDefinition> list = new LinkedList<>();
        for (BeanDefinition beanDefinition : beanDefinitionsByType) {
            String beanClassName;
            if(!beanDefinition.getPropertyValues().contains("interface")){
                continue;
            }
            beanClassName = beanDefinition.getPropertyValues().get("interface").toString();
            Class son;
            try {
                son = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                continue;
            }

            if(clazz.isAssignableFrom(son)){
                list.add(beanDefinition);
            }
        }
        return list;
    }
}

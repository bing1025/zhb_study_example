package com.example.zhb.study.demo.day2.spring;

import com.example.zhb.study.demo.day2.spring.componentscanpackage.ServiceC;
import com.example.zhb.study.demo.day2.spring.componentscanpackage.EntityD;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: zhouhb
 * @date: 2021/06/25/20:45
 * @Description:
 */
public class MyImportBeanDefinitonRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(EntityD.class);
        RootBeanDefinition rootBeanDefinition1 = new RootBeanDefinition(ServiceC.class);
        registry.registerBeanDefinition("serviceD",rootBeanDefinition);
        registry.registerBeanDefinition("serviceC",rootBeanDefinition);
    }
}

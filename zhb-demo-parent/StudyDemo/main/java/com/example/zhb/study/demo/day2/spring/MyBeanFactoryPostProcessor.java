package com.example.zhb.study.demo.day2.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 实现 BeanFactoryPostProcessor 接口
 *
 * 总结
 * BeanFactoryPostProcessor是用来处理BeanFacoty中Bean属性的后置处理器
 * BeanFactoryPostProcessor接口只定义了一个简单的方法postProcessBeanFactory()
 * BeanFactoryPostProcessor接口允许修改上下文中Bean的定义（definitions），可以调整Bean的属性
 * 上下文可以自动检测BeanFactoryPostProcessor，并且在Bean实例化之前调用
 * 注意事项：BeanFactoryPostProcessor可以在Bean实例化之前修改Bean的属性，
 * 但不适合在BeanFactoryPostProcessor中做Bean的实例化，这样会导致一些意想不到的副作用，就是不要把Spring玩坏了_ ，
 * 若需要做Bean的实例化可以使用BeanPostProcessor
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
 
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		// TODO Auto-generated method stub
		//BeanFactoryPostProcessor可以修改BEAN的配置信息而BeanPostProcessor不能
		//我们在这里修改postProcessorBean的username注入属性
		System.out.println("调用自定义BeanFactoryPostProcessor");
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("entitlement");
		System.out.println("开始修改属性的值");
		beanDefinition.getPropertyValues().add("name","zhb---update--");
		//beanDefinition.setScope("prototype");
	}
 
}
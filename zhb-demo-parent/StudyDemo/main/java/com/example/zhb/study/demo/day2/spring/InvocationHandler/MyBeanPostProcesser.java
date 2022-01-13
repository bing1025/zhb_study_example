package com.example.zhb.study.demo.day2.spring.InvocationHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
// @Component   // ??? 后置处理前反射的话 entitlement 会报错 ？？？
public class MyBeanPostProcesser implements BeanPostProcessor {  
    private Map map = new ConcurrentHashMap(100);
  
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("============"+beanName);
        MyProxy proxy = new MyProxy();  
  
        if (beanName.contains("DB")) {  
            return bean;  
        }  
  
        if (bean.toString().contains("Proxy")) {  
            log.info(beanName + "为代理类,不进行再次代理!");  
            return bean;  
        }  
        if (beanName.contains("TransactionTemplate")) {  
            log.info(beanName + "为TransactionTemplate类,不进行再次代理!该类为:" + bean);  
            return bean;  
        }  
  
        if (map.get(beanName) != null) {  
            log.info(beanName + "已经代理过,不进行再次代理!");  
            return map.get(beanName);  
        }  
        proxy.setObj(bean);  
        proxy.setName(beanName);  
        Class[] iterClass = bean.getClass().getInterfaces();  
        if (iterClass.length > 0) {  
            Object proxyO = Proxy.newProxyInstance(bean.getClass().getClassLoader(), iterClass, proxy);  
            map.put(beanName, proxyO);  
            return proxyO;  
        } else {  
            log.info(beanName + "么有接口不进行代理!");  
            return bean;  
        }  
    }  
  
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {  
        return bean;  
    }  
  
}  
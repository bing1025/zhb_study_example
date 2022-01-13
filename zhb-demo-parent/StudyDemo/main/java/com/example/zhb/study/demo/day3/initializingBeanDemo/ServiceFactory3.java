package com.example.zhb.study.demo.day3.initializingBeanDemo;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * service实现类的工厂选择器：选择使用具体的哪一个实现类来工作
 * 方式3：使用spring的工具类 BeanFactoryUtils.beansOfTypeIncludingAncestors
 * @Author: zhouhb
 * @date: 2021/09/24/11:13
 * @Description:
 */
@Component("serviceFactory3")
public class ServiceFactory3 implements ApplicationContextAware, InitializingBean {

    private ApplicationContext  applicationContext;

    Map<String,ServiceInterface> serviceInterfaceMap = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ServiceInterface> stringServiceInterfaceMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(this.applicationContext, ServiceInterface.class);
        serviceInterfaceMap.putAll(stringServiceInterfaceMap);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    public ServiceInterface getServiceInterfaceByVersion(String version){
        ServiceInterface serviceInterface = serviceInterfaceMap.get(version);
        return serviceInterface;
    }
}

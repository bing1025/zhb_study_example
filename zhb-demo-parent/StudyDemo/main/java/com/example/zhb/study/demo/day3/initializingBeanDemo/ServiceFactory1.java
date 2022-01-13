package com.example.zhb.study.demo.day3.initializingBeanDemo;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * service实现类的工厂选择器：选择使用具体的哪一个实现类来工作
 * 方法1：使用spring的注解，让spring帮我们直接注入，list形式注入
 * @Author: zhouhb
 * @date: 2021/09/24/11:13
 * @Description:
 */
@Component("serviceFactory1")
public class ServiceFactory1 implements InitializingBean {

    @Autowired
    List<ServiceInterface>  serviceInterfaceList;

    Map<String,ServiceInterface> serviceInterfaceMap = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (ServiceInterface serviceInterface : serviceInterfaceList) {
            serviceInterfaceMap.put(serviceInterface.version(),serviceInterface);
        }
    }

    public ServiceInterface getServiceInterfaceByVersion(String version){
        ServiceInterface serviceInterface = serviceInterfaceMap.get(version);
        return serviceInterface;
    }

}

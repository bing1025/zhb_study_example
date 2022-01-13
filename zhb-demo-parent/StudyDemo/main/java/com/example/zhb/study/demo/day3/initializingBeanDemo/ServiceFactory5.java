package com.example.zhb.study.demo.day3.initializingBeanDemo;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * service实现类的工厂选择器：选择使用具体的哪一个实现类来工作
 * 方法5：ServiceEnum3枚举示例的使用
 * @Author: zhouhb
 * @date: 2021/09/24/11:13
 * @Description:
 */
@Component("serviceFactory5")
public class ServiceFactory5 implements InitializingBean {

    Map<String,ServiceInterface> serviceInterfaceMap = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (ServiceEnum3 serviceEnum3 : ServiceEnum3.values()) {
            Class<?> aClass = Class.forName(serviceEnum3.getClassPath());
            ServiceInterface serviceInterface = (ServiceInterface)aClass.newInstance();
            serviceInterfaceMap.put(serviceEnum3.getVersion(),serviceInterface);
        }
    }

    public ServiceInterface getServiceInterfaceByVersion(String version){
        ServiceInterface serviceInterface = serviceInterfaceMap.get(version);
        return serviceInterface;
    }

}

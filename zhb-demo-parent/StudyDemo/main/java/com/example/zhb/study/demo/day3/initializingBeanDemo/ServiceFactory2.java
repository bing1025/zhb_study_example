package com.example.zhb.study.demo.day3.initializingBeanDemo;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * service实现类的工厂选择器：选择使用具体的哪一个实现类来工作
 * 方法2：使用spring的注解，让spring帮我们直接注入，Map形式注入
 * @Author: zhouhb
 * @date: 2021/09/24/11:13
 * @Description:
 */
@Component("serviceFactory2")
public class ServiceFactory2 implements InitializingBean {

    /**
     * 向map中注入所有类型为 ServiceInterface 的bean,其中键为bean的名称，value为bean的实例
     */
    @Resource
    Map<String,ServiceInterface>  serviceInterfaceResourceMap;

    Map<String,ServiceInterface> serviceInterfaceMap = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        serviceInterfaceMap.putAll(serviceInterfaceResourceMap);

        //或者
        /*for (Map.Entry<String, ServiceInterface> stringServiceInterfaceEntry : serviceInterfaceResourceMap.entrySet()) {
            serviceInterfaceMap.put(stringServiceInterfaceEntry.getKey(),stringServiceInterfaceEntry.getValue());
        }*/
    }

    public ServiceInterface getServiceInterfaceByVersion(String version){
        ServiceInterface serviceInterface = serviceInterfaceMap.get(version);
        return serviceInterface;
    }

}

package com.example.zhb.study.demo.day2.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhouhb
 * @date: 2021/06/25/19:54
 * @Description:
 */
@Configuration
// 方法1
@ComponentScan
// 方法2
//@ComponentScan(basePackages={"com.example.zhb.study.demo.day2.spring.a","com.example.zhb.study.demo.day2.spring.componentscanpackage"})
// 方法3
//@Import(value={ServiceC.class, ServiceE.class, ServiceF.class})
// 方法4
//@Import(value={MyImportSelector.class, ServiceE.class})
// 方法5
//@Import(value={MyImportBeanDefinitonRegistrar.class, ServiceE.class})
public class BeanConfig {

    @Bean(name="entitlement")
    public Entitlement entitlement() {
        Entitlement ent= new Entitlement();
        ent.setName("Entitlement ");
        ent.setAge(1);
        return ent;
    }

    /*@Bean
    public BeanFactoryPostProcessor custom(){
        return new MyBeanFactoryPostProcessor();
    }*/


}

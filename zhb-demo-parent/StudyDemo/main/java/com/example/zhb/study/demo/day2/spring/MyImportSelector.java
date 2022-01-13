package com.example.zhb.study.demo.day2.spring;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Import（value={xxxx}）注解的value值为自定义的选择器类，此选择器实现ImportSelector接口
 *
 * 选择器通过全类名选择需要实例化的组件
 * @Author: zhouhb
 * @date: 2021/06/25/20:39
 * @Description:
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.example.zhb.study.demo.day2.spring.componentscanpackage.ServiceD",
                "com.example.zhb.study.demo.day2.spring.ServiceA",
        "com.example.zhb.study.demo.day2.spring.a.ServiceF"};
    }
}

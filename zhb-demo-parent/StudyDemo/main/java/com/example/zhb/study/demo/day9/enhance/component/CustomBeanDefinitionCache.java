package com.example.zhb.study.demo.day9.enhance.component;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author: zhouhb
 * @date: 2021/10/25/17:06
 * @Description:
 */
@Slf4j
public class CustomBeanDefinitionCache {
    private final BeanDefinitionRegistry registry;
    private final Map<Class<? extends Annotation>, Collection<BeanDefinition>> annoToBeanBefinitionMap = new HashMap<>();
    private final Map<Class<?>, Collection<BeanDefinition>> typeToBeanDefinitionMap = new HashMap<>();
    private final Map<BeanDefinition, String> beanDefinitionToNameMap = new HashMap<>();

    public CustomBeanDefinitionCache(BeanDefinitionRegistry registry) {
        this.registry = registry;

        // 遍历所有类定义
        for (String beanDefinitionName : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            beanDefinitionToNameMap.put(beanDefinition,beanDefinitionName);
            // 获取类的全路径名
            String beanClassName = getBeanClassName(beanDefinition);

            if (StringUtils.isBlank(beanClassName)) {
                continue;
            }

            Class beanClass;
            try {
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                continue;
            }

            // 初始化注解和类定义的关联关系
            Annotation[] annotations = beanClass.getAnnotations();
            if(!ObjectUtils.isEmpty(annotations)){
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> annotationClass = annotation.annotationType();
                    Collection<BeanDefinition> beanDefinitions = annoToBeanBefinitionMap.get(annotationClass);
                    if(null == beanDefinitions){
                        beanDefinitions = Lists.newLinkedList();
                        annoToBeanBefinitionMap.put(annotationClass,beanDefinitions);
                    }
                    beanDefinitions.add(beanDefinition);
                }
            }

            // 初始化类和类定义的关联关系
            Collection<Class> classes = getAllClass(beanClass);
            for (Class aClass : classes) {
                Collection<BeanDefinition> beanDefinitions = typeToBeanDefinitionMap.get(aClass);
                if(null == beanDefinitions){
                    beanDefinitions = Lists.newLinkedList();
                    typeToBeanDefinitionMap.put(aClass,beanDefinitions);
                }
                beanDefinitions.add(beanDefinition);
            }
        }
        // log.debug("容器收集处理完成==annoToBeanBefinitionMap：{}", JSONObject.toJSONString(annoToBeanBefinitionMap));
    }

    private Collection<Class> getAllClass(Class clazz) {
        List<Class> classes = Lists.newLinkedList();
        getAllClass(clazz,classes);
        return classes;
    }

    private void getAllClass(Class clazz, List<Class> classes) {
        if(clazz == null || clazz == Object.class){
            return;
        }
        classes.add(clazz);
        getAllClass(clazz.getSuperclass(),classes);
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            classes.add(anInterface);
            getAllClass(anInterface,classes);
        }
    }

    private String getBeanClassName(BeanDefinition beanDefinition) {
        return beanDefinition.getBeanClassName();
    }

    public Collection<BeanDefinition> getBeanDefinitionsByAnnotation(Class<? extends Annotation> annotationType) {
        return new ArrayList<>(annoToBeanBefinitionMap.getOrDefault(annotationType,new ArrayList<>(0)));
    }

    public Collection<BeanDefinition> getBeanDefinitionsByType(Class<ReferenceBean> clazz) {
        Collection<BeanDefinition> beanDefinitions = typeToBeanDefinitionMap.get(clazz);
        if(null == beanDefinitions){
            return new ArrayList<>();
        }
        return new ArrayList<>(beanDefinitions);
    }

    public String getBeanDefinitionId(BeanDefinition beanDefinition) {
        return beanDefinitionToNameMap.get(beanDefinition);
    }

    /**
     * 移除类定义
     * @param beanName
     * @return
     */
    public BeanDefinition delete(String beanName) {
        BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
        registry.removeBeanDefinition(beanName);
        beanDefinitionToNameMap.remove(beanDefinition);
        annoToBeanBefinitionMap.values().forEach(beanDefinitions ->{
            Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
            while (iterator.hasNext()) {
                BeanDefinition next = iterator.next();
                if (next == beanDefinition) {
                    iterator.remove();
                }
            }
        });
        typeToBeanDefinitionMap.values().forEach(beanDefinitions ->{
            Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
            while (iterator.hasNext()) {
                BeanDefinition next = iterator.next();
                if (next == beanDefinition) {
                    iterator.remove();
                }
            }
        });
        if(log.isDebugEnabled()){
            log.debug("自定义Bean处理--已移除Bean定义 ---》{}（{}）",beanName,beanDefinition.getBeanClassName());
        }
        return beanDefinition;
    }

    /**
     * 使用新的类定义替换老的类定义
     * @param beanDefinition  新的类定义
     * @param replaceBeanDefinition   被替换的老的类定义
     */
    public void replace(BeanDefinition beanDefinition, BeanDefinition replaceBeanDefinition) {
        if(beanDefinition==replaceBeanDefinition){
            return;
        }
        String replaceBeanDefinitionName = getBeanDefinitionId(replaceBeanDefinition);
        delete(replaceBeanDefinitionName);
        // 配置别名，将替换的类replaceBeanDefinitionName都用 getBeanDefinitionId(beanDefinition) 来处理
        registry.registerAlias(getBeanDefinitionId(beanDefinition),replaceBeanDefinitionName);
        if(log.isDebugEnabled()){
            log.debug("自定义Bean处理--使用{}({})替换了{}({})",getBeanDefinitionId(beanDefinition),
                    beanDefinition.getBeanClassName(),replaceBeanDefinitionName,replaceBeanDefinition.getBeanClassName()
                    );
        }
    }
}

package com.example.zhb.study.demo.day28;


import org.junit.platform.commons.util.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * CompletableFuture执行多个异步任务,将结果合并返回
 * @Author: zhouhb
 * @date: 2023/06/15/11:17
 * @Description:
 */
public class AnnotationHandle {

    public static void handleAnnotation(String groups,ParentDTO parentDTO) {
        //获取自定义注解的配置的所有bean
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(CustomAnnotation.class);

        // 使用SortedMap类，通过key进行默认升序排序
        SortedMap<Integer,Class<? extends ServiceInterFace>> rstMap = new TreeMap<>();

        for (String key : beansWithAnnotation.keySet()) {
            //获取指定bean的注解为CustomAnnotation的注解属性
            final CustomAnnotation annotation = AnnotationUtils.findAnnotation(beansWithAnnotation.get(key).getClass(), CustomAnnotation.class);
            if (null != annotation && 0 != annotation.order()) {
                int order = annotation.order();
                String[] groups1 = annotation.groups();
                for (String s : groups1) {
                    if(s.equals(groups)){
                        rstMap.put(order, (Class<? extends ServiceInterFace>) beansWithAnnotation.get(key).getClass());
                    }
                }
            }

        }

        // 循环遍历排序后的方法
        for (Map.Entry<Integer, Class<? extends ServiceInterFace>> entry : rstMap.entrySet()) {
            //1、创建运行时类
            Class<? extends ServiceInterFace> clazz = entry.getValue();
            //2、创建Method对象
            Method m1 = null;
            try {
//                //3、创建运行时类所对应的对象
//                m1 = clazz.getMethod("getOrder");
//                Service p = (Service) clazz.newInstance();
//                //4、调用方法
//                System.out.println("===="+m1.invoke(p));
                ServiceInterFace serviceInterFace = clazz.newInstance();
                System.out.println("===="+serviceInterFace.getOrder(parentDTO));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }





}

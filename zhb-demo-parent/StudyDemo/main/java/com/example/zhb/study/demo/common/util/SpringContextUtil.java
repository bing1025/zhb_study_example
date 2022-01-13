package com.example.zhb.study.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/***
 *
 * @author Xiaojie.Li
 * @description spring上下文工具
 * @date 2021/6/1
 */
@Component
@Slf4j
public class SpringContextUtil implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    private static Environment ENVIRONMENT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.APPLICATION_CONTEXT = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringContextUtil.ENVIRONMENT = environment;
    }

    private static <T> Optional<T> get(Function<ApplicationContext,T> function){
        if (APPLICATION_CONTEXT == null){
            log.warn("");
            return Optional.empty();
        }

        return Optional.ofNullable(function.apply(APPLICATION_CONTEXT));
    }

    public static <T> Optional<T> getBean(Class<T> tClass){
        return get(applicationContext -> applicationContext.getBean(tClass));
    }

    public static <T> T forceGetBean(Class<T> tClass){
        return getBean(tClass).orElseThrow(()->new RuntimeException("not such bean class: " + tClass));
    }

    private static Optional<String> getProperty(String propertyName){
        return Optional.ofNullable(ENVIRONMENT.getProperty(propertyName));
    }

    public static String forceGetProperty(String propertyName){
        return getProperty(propertyName).orElseThrow(()->new RuntimeException("no such property:" + propertyName));
    }

    public static Map<String,Object> getBeansWithAnnotation(Class<? extends Annotation> cls){
        return APPLICATION_CONTEXT.getBeansWithAnnotation(cls);
    }

}

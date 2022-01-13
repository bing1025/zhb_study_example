package com.example.zhb.study.demo.day2.spring.InvocationHandler;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class MyProxy implements InvocationHandler {
  
    private Object obj;  
  
    private String name;  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public Object getObj() {  
        return obj;  
    }  
  
    public void setObj(Object obj) {  
        this.obj = obj;  
    }  
  
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
        System.out.println("begin================" + "bean 名称为【" + name + "】方法为【" + method.getName() + "】========="  
                + obj.getClass());  
        log.error("begin================" + "bean 名称为【" + name + "】方法为【" + method.getName() + "】========="  
                + obj.getClass());
        //setMethod.invoke(clazz.newInstance(), new Object[]{columnValue});
        return method.invoke(obj, args);  
    }  
  
    public void printDetail(String detail) {  
        log.error(detail);  
    }  
  
}  
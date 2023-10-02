package com.zhb.spring.cloud.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java 反射取值
 * @Author: zhouhb
 * @date: 2023/06/14/20:59
 * @Description:
 */
public class ParamsReflect {

    // 一 通过属性取值赋值

    /**
     * 属性取值
     * @param obj 实例
     * @param field 取值字段
     * @return
     */
    public static String getFieldValueByClass(Object obj, String field) {
        Class<?> aClass = obj.getClass();
        try {
            Field f = aClass.getDeclaredField(field);
            f.setAccessible(true);
            return (String) f.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 属性赋值
     * @param obj 实例
     * @param field 赋值的字段
     * @param value 赋的值
     * @return
     */
    public static String setFieldValueByClass(Object obj,String field,String value) {

        Class<?> aClass = obj.getClass();
        Field f = null;
        try {
            f = aClass.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

//    二 通过方法取值赋值
//    案列是针对属性的set,get方法，如果是自定义方法，字段参数直接传方法名，去掉手动拼接setXxx,getXxx那一块代码即可，原理不变

    /**
     * 反射取值
     * @param obj 实例对象
     * @param field 取值字段
     * @return
     */
    public static String getMethodValueByClass(Object obj,String field) {

        //将字段首字母大写并取出 x > X
        String firstUpper = String.valueOf(field.charAt(0)).toUpperCase();

        //利用StringBuilder 特有的 replace 方法转换将 xxx > Xxx
        StringBuilder sb = new StringBuilder(field);
        sb.replace(0,1,firstUpper);

        //拼接新的字段 getXxx
        field = "get"+sb.toString();

        try {
            //取值
            Class clazz = obj.getClass();
            return clazz.getMethod(field).invoke(obj).toString();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 方法赋值
     * @param obj 实例对象
     * @param field 字段
     * @param value 赋的值
     * @return
     */
    public static String setMethodValueByClass(Object obj, String field, String value) {

        String s1 = String.valueOf(field.charAt(0)).toUpperCase();
        StringBuilder sb = new StringBuilder(field);
        sb.replace(0, 1, s1);

        field = "set" + sb.toString();

        try {
            Class clazz = obj.getClass();

            //设置方法参数String, 可设置多个参数，clazz.getMethod(field, String.class,Integer.class....) 等对应你属性里面形参即可
            Method method = clazz.getMethod(field, String.class);

            //填充值，对应以上参数个数即可
            return method.invoke(obj, value).toString();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }


}

package com.example.zhb.study.demo.day9.enhance.anno;

import java.lang.annotation.*;

/**
 * @Author: zhouhb
 * @date: 2021/10/25/17:37
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MyBeanDefinitionHandler {
    int order() default Integer.MAX_VALUE;
}

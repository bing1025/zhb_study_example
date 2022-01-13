package com.example.zhb.study.demo.day10.handler;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义TaskHandler注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component  // @Component表示在spring 启动过程中，会扫描到并且注入到容器中
public @interface TaskHandler {
    String taskType() default "";
}
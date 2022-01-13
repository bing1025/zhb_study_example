package com.example.zhb.study.demo.day9.enhance.anno;

import java.lang.annotation.*;

/**
 * @Author: zhouhb
 * @date: 2021/10/25/15:33
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtReplaceBean {

    /**
     * 需要被替换的类
     * @return
     */
    Class[] classTypes() default {};

    /**
     * 需要被移除的类
     * @return
     */
    Class[] deleteClasses() default {};
}

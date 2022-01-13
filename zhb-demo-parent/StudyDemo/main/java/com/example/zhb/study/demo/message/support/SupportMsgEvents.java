package com.example.zhb.study.demo.message.support;

import java.lang.annotation.*;

/**
 * @author Xiaojie.Li
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SupportMsgEvents {

    Class<? extends MessageEvent>[] value();
}

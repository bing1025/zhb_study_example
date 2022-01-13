package com.example.zhb.study.demo.day15.initEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化枚举
 * @Author: zhouhb
 * @date: 2021/11/18/18:07
 * @Description:
 */
@Slf4j
public class EnumInitializer {

    public static void initEnums(Class... classes) {
        log.info("initEnums start ");
        for (Class clazz : classes) {
            try {
                Class.forName(clazz.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        log.info("initEnums end ");
    }
}

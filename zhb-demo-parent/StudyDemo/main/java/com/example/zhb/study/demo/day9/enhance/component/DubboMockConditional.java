package com.example.zhb.study.demo.day9.enhance.component;

/**
 * @Author: zhouhb
 * @date: 2021/10/25/15:38
 * @Description:
 */
public class DubboMockConditional {

    public static boolean isEnabled(){

        // 条件一：配置文件中是否要开启dubbo mock 功能
        // 条件二：如果配置文件是关闭mock功能的，根据环境来判断，dev环境默认开启mock功能
        return true;
    }
}

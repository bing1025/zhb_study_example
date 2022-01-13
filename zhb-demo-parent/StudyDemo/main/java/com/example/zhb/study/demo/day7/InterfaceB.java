package com.example.zhb.study.demo.day7;

/**
 * B接口：表示某一中类型的接口
 * @Author: zhouhb
 * @date: 2021/10/18/17:50
 * @Description:
 */
public interface InterfaceB {

    /**
     * 是否包含某事件code
     * @param eventCode
     * @return
     */
    boolean containsEventCode(String eventCode);

    /**
     * 正真的调用的业务方法
     * @return
     */
    String doEventB();
}

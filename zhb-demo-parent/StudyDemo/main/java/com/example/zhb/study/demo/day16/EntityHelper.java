package com.example.zhb.study.demo.day16;

/**
 * @Author: zhouhb
 * @date: 2021/11/19/11:38
 * @Description:
 */
public class EntityHelper {

    public EntityHelper() {
    }

    /**
     *  static 用法
     * @return
     */
    public static String getMsg(){
        return "原始的 答复";

    }

    public  String getMsg2(){
        return "原始的 答复 222";
        // getPrivateTest();
    }

    private void getPrivateTest() {
    }

    public  String getMsg3(){
        return "原始的 答复 333";
    }

    private String getMsg4(){
        return "原始的 答复 444";
    }
}

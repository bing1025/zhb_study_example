package com.example.zhb.study.demo.day16;

/**
 * @Author: zhouhb
 * @date: 2021/11/19/11:40
 * @Description:
 */
public class EntityHelperExtend extends EntityHelper{

    public static String getMsg(){
        return "这是继承后的实现。。。";
    }

    @Override
    public  String getMsg2(){
        return "这是继承后的实现 222";
        //getPrivateTest();
    }

    private void getPrivateTest() {
    }

    /*@Override
    public  String getMsg4(){
        return "这是继承后的实现 222";
    }*/
}

package com.example.zhb.study.demo.day1.jvm;

/**
 * java 栈 最大深度
 * https://www.cnblogs.com/rocky-fang/p/8367018.html
 *
 * 当线程请求栈的深度超过当前 Java 虚拟机栈的最大深度时，抛出 StackOverFlowError 异常
 *
 * 目的是调整jvm 栈大小 主要是 更改 -Xss 的值
 *
 * -Xss2m
 * java.lang.StackOverflowError
 * stack height:41272
 *
 * -Xss5m
 * java.lang.StackOverflowError
 * stack height:292295
 *
 * -Xss10m
 * java.lang.StackOverflowError
 * stack height:622735
 */
public class JavaStackTest {
    
    private int count = 0;
    
    public void testStack(){
        count++;
        testStack();
    };
    
    public void test(){
        try {
            testStack();
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("stack height:"+count);
        }
    }

    public static void main(String[] args) {
        new JavaStackTest().test();
    }


}
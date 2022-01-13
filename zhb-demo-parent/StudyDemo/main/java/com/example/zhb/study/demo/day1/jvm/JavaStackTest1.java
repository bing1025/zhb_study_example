package com.example.zhb.study.demo.day1.jvm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * java 栈 最大深度
 * https://blog.csdn.net/peng_zhanxuan/article/details/104329922
 *
 * 当线程请求栈的深度超过当前 Java 虚拟机栈的最大深度时，抛出 StackOverFlowError 异常
 *
 * 测试方法参数个对栈深度的影响 ：-Xss2m都保证是2M
 *
 *JavaStackTest1 三个方法的次数：
 *
 * java.lang.StackOverflowError
 * count1 stack height:21672
 * java.lang.StackOverflowError
 * count3 stack height:12060
 * java.lang.StackOverflowError
 * count2 stack height:18979
 *
 * 由此可以看出，局部变量表内容越多，栈帧越大，栈深度越小。
 * 知道了栈深度，该怎么用呢？对JVM调优有什么用呢？
 *
 * 当我们定义的方法参数和局部变量过多，字节过大，考虑到可能会导致栈深度多小，可能使程序出现错误。
 *
 * 这个时候就需要手动的增加栈的深度，避免出错。
 *
 */
public class JavaStackTest1 {

    private AtomicInteger count0 = new AtomicInteger(0);

    private AtomicInteger count1 = new AtomicInteger(0);

    private AtomicInteger count2 = new AtomicInteger(0);

    private AtomicInteger count3 = new AtomicInteger(0);

    public void testStack(){
        count0.addAndGet(1);
        testStack();
    };

    public void testStack(String a){
        count1.addAndGet(1);
        testStack(a);
    };

    public void testStack(String a,String b){
        count2.addAndGet(1);
        testStack(a,b);
    };

    public void testStack(String a,String b,String c){
        count3.addAndGet(1);
        testStack(a,b,c);
    };

    public void test0(){
        try {
            testStack();
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("count0 stack height:"+count0.get());
        }
    }

    public void test(){
        try {
            String a ="z";
            testStack(a);
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("count1 stack height:"+count1.get());
        }
    }

    public void test1(){
        try {
            String a ="z";
            String b ="h";

            testStack(a,b);
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("count2 stack height:"+count2.get());
        }
    }

    public void test2(){
        try {
            String a ="z";
            String b ="h";
            String c ="b";
            testStack(a,b,c);
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("count3 stack height:"+count3.get());
        }
    }

    public static void main(String[] args) {

       // new Thread(() -> new JavaStackTest1().test0()).start();
        // 测试引申问题，无参数的怎么就出现了意想不到的问题

        new Thread(){
            @Override
            public void run(){
                new JavaStackTest1().test();
            }
        }.start();

        new Thread(() -> new JavaStackTest1().test1()).start();

        new Thread(() -> new JavaStackTest1().test2()).start();
    }

}
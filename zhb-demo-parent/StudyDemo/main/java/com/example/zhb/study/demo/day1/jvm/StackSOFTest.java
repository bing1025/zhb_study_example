package com.example.zhb.study.demo.day1.jvm;

/**
 * 栈空间不足——StackOverflowError实例
 * 栈空间不足时，须要分下面两种状况处理：
 * 线程请求的栈深度大于虚拟机所容许的最大深度，将抛出StackOverflowError
 * 虚拟机在扩展栈深度时没法申请到足够的内存空间，将抛出OutOfMemberError
 */
public class StackSOFTest {

    int depth = 0;

    public void sofMethod(){
        depth ++ ;
        sofMethod();
    }

    public static void main(String[] args) {
        StackSOFTest test = null;
        try {
            test = new StackSOFTest();
            test.sofMethod();
        } finally {
            System.out.println("递归次数："+test.depth);
        }
    }

    // 递归次数：33459
}
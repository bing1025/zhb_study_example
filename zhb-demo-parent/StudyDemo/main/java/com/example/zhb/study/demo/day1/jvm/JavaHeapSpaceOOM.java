package com.example.zhb.study.demo.day1.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 写代码实现堆溢出、栈溢出、永久代溢出、直接内存溢出
 * 堆溢出(OutOfMemoryError:Java heap space)
 *
 * http://www.javashuo.com/article/p-qyxjnzof-bc.html
 *
 * @Author: zhouhb
 * @date: 2021/06/08/17:34
 * @Description:
 */
public class JavaHeapSpaceOOM {
    /** * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError */
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        int i=0;
        while(true){
            list.add(new byte[5*1024*1024]);
            System.out.println("分配次数："+(++i));
        }
    }

    /*
    分配次数：1
    分配次数：2
    分配次数：3
    java.lang.OutOfMemoryError: Java heap space
    Dumping heap to java_pid11556.hprof ...
    Heap dump file created [17433188 bytes in 0.011 secs]
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at com.example.zhb.study.demo.day1.jvm.JavaHeapSpaceOOM.main(JavaHeapSpaceOOM.java:20)
    *
    附：dump文件会在项目的根目录下生成
     */
}

package com.example.zhb.study.demo.day1.jvm;

/**
 * 使用jstack来看一下线程堆栈信息
 * 堆栈写的很明显，它告诉我们 Found one Java-level deadlock，然后指出造成死锁的两个线程的内容。
 * 然后，又通过 Java stack information for the threads listed above来显示更详细的死锁的信息。
 * 他说Thread-1在想要执行第40行的时候，当前锁住了资源<0x00000007d6aa2ca8>,
 * 但是他在等待资源<0x00000007d6aa2c98>
 *  Thread-0在想要执行第27行的时候，当前锁住了资源<0x00000007d6aa2c98>,
 *  但是他在等待资源<0x00000007d6aa2ca8>
 *  由于这两个线程都持有资源，并且都需要对方的资源，所以造成了死锁。 原因我们找到了，就可以具体问题具体分析，解决这个死锁了
 */
public class JStackDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockclass(true));//建立一个线程
        Thread t2 = new Thread(new DeadLockclass(false));//建立另一个线程
        t1.start();//启动一个线程
        t2.start();//启动另一个线程
    }
}
class DeadLockclass implements Runnable {
    public boolean falg;// 控制线程
    DeadLockclass(boolean falg) {
        this.falg = falg;
    }
    public void run() {
        /**
         * 如果falg的值为true则调用t1线程
         */
        if (falg) {
            while (true) {
                synchronized (Suo.o1) {
                    System.out.println("o1 " + Thread.currentThread().getName());
                    synchronized (Suo.o2) {
                        System.out.println("o2 " + Thread.currentThread().getName());
                    }
                }
            }
        }
        /**
         * 如果falg的值为false则调用t2线程
         */
        else {
            while (true) {
                synchronized (Suo.o2) {
                    System.out.println("o2 " + Thread.currentThread().getName());
                    synchronized (Suo.o1) {
                        System.out.println("o1 " + Thread.currentThread().getName());
                    }
                }
            }
        }
    }
}

class Suo {
    static Object o1 = new Object();
    static Object o2 = new Object();
}
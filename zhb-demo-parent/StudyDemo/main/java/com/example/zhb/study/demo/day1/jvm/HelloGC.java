package com.example.zhb.study.demo.day1.jvm;

//下面的演示要注意先启动HelloGC
	public class HelloGC {
	    public static void main(String[] args) throws InterruptedException {
	        System.out.println("*****HelloGC");
	        //如何查看一个正在运行中的java程序，它的某个jvm参数是否开启？具体值是多少?
	        Thread.sleep(600000);
	    }
	}
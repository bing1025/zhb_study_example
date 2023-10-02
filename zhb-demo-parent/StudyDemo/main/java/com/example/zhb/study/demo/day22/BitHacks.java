package com.example.zhb.study.demo.day22;

/**
 * @Author: zhouhb
 * @date: 2022/03/10/10:56
 * @Description:
 */
public class BitHacks {


    /*1．判断奇偶
    只要根据最未位是0还是1来决定，为0就是偶数，为1就是奇数。因此可以用if ((a & 1) == 0)代替if (a % 2 == 0)来判断a是不是偶数。
    下面程序将输出0到100之间的所有奇数。*/
    public static void test1() {
        for (int i = 0; i < 100; i++){
            if((i&1)==0){
                System.out.println("偶数"+i);
            }else if((i&1)==1){
                System.out.println("奇数"+i);
            }
        }
    }

  /*  2：交换两数
    一般的写法是：:
    void Swap(int a, int b)
    {
        if (a != b)
        {
            int c = a;
            a = b;
            b = c;
        }
    }
    可以用位操作来实现交换两数而不用第三方变量：
    void Swap(int a, int b)
    {
        if (a != b)
        {
            a ^= b;
            b ^= a;
            a ^= b;
        }
    }*/

    public static void test2() {
        int a = 1, b = 2;
        System.out.println("a为"+a+"==b为"+b);
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a为"+a+"==b为"+b);
    }

   /*3 利用或操作 | 和空格将英文字符转换为小写
            ('a' | ' ') = 'a'
            ('A' | ' ') = 'a'*/

    public static void test3() {
        System.out.println(('a' | ' '));
        System.out.println(('A' | ' '));
    }

    /*4:利用与操作 & 和下划线将英文字符转换为大写
            ('b' & '_') = 'B'
            ('B' & '_') = 'B'*/
    public static void test4() {
        System.out.println(('b' & '_'));
        System.out.println(('B' & '_'));
    }

   /* 5:利用异或操作 ^ 和空格进行英文字符大小写互换
            ('d' ^ ' ') = 'D'
            ('D' ^ ' ') = 'd'*/

    public static void test5() {
        System.out.println(('d' ^ ' '));
        System.out.println(('D' ^ ' '));
    }

   /* 6:判断两个数是否异号
    int x = -1, y = 2;
    boolean f = ((x ^ y) < 0); // true
    int x = 3, y = 2;
    boolean f = ((x ^ y) < 0); // false
    这个技巧还是很实用的，利用的是补码编码的符号位。
    如果不用位运算来判断是否异号，需要使用 if else 分支，还挺麻烦的。
    读者可能想利用乘积或者商来判断两个数是否异号，但是这种处理方式可能造成溢出，从而出现错误*/

    public static void test6() {
        int x = -1, y = 2;
        boolean f = ((x ^ y) < 0); // true
        int x1 = 3, y1 = 2;
        boolean f1 = ((x1 ^ y1) < 0); // false
        System.out.println("f为"+f+"==f1为"+f1);
    }

    /* 7:加一，减一操作
    加一
    int n = 1;
    n = -~n;
    // 现在 n = 2
    减一
    int n = 2;
    n = ~-n;
    // 现在 n = 1
     */
    public static void test7() {
        int n = 1;
        n = -~n; // 现在 n = 2
        int m = 2;
        m = ~-m; // 现在 m = 1
        System.out.println("n为"+n+"==m为"+m);
    }


    /*
    让你返回 n 的二进制表示中有几个 1。
    因为 n & (n - 1) 可以消除最后一个 1，
    所以可以用一个循环不停地消除 1 同时计数，直到 n 变成 0 为止。*/

    int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }

    /*判断一个数是不是 2 的指数
    力扣第 231 题「 2 的幂」就是这个问题。
    一个数如果是 2 的指数，那么它的二进制表示一定只含有一个 1：
            2^0 = 1 = 0b0001
            2^1 = 2 = 0b0010
            2^2 = 4 = 0b0100
    如果使用 n & (n-1) 的技巧就很简单了（注意运算符优先级，括号不可以省略）：*/

    boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        return (n & (n - 1)) == 0;
    }

   /* 查找只出现一次的元素
    这是力扣第 136 题「 只出现一次的数字」：
    这里就可以运用异或运算的性质：
    一个数和它本身做异或运算结果为 0，即 a ^ a = 0；一个数和 0 做异或运算的结果为它本身，即 a ^ 0 = a。
    对于这道题目，我们只要把所有数字进行异或，成对儿的数字就会变成 0，落单的数字和 0 做异或还是它本身，
    所以最后异或的结果就是只出现一次的元素：*/
    int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) {
            res ^= n;
        }
        return res;
    }

    /*以上便是一些有趣/常用的位操作。其实位操作的技巧很多，有一个叫做 Bit Twiddling Hacks 的外国网站收集了几乎所有位操作的黑科技玩法，
    感兴趣的读者可以查看：
    http://graphics.stanford.edu/~seander/bithacks.html#ReverseParallel*/

}

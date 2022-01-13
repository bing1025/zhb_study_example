
java | BitMap原理解析 
https://blog.csdn.net/woshilijiuyi/article/details/88778214

概述
在一些数据量比较大的场景中，做一些查重、排序，一般的方法难以实现。数据量过大，会占用较大的内存，常用的处理方式有两种：BitMap（位图法）和布隆过滤。
本篇针对以下题目来看一下如何用位图法来实现：10亿个正整数，给定一个数值，如何快速排定该数值是否在10亿个正整数当中？
位图法的思想类似于hash寻址，首先初始化一个int数组，每个元素对应32位比特，将10亿个元素分别读入内存，对int数组中的元素比特位进行标记，如果存在，标记为1即可。标记完之后，即可快速判定某个元素是否在10亿个正整数当中，时间复杂度为O（1）。

需要多大的内存？
10亿个元素需要10亿个比特位，10亿/8/1024/1024 = 120 M，相比原来节省了 32 倍内存。
注意：这是最小使用内存。原因后面会介绍。

需要申请多大的数组？
array[0]:可表示0~31
array[1]:可表示32~63
array[2]可表示64~95
…
数组长度为10亿/32 +1即可。

每个int类型可标识32个整数，如下图：

寻址
比如元素 119，如何确定其对应的数组比特位 index ？
1）确定数组 index：119/32 = 3.7，也就是第 4 个数组元素，a[3] 的位置。
2）确定比特位 index：119%32 = 23，第23个比特位。
3）将比特位设置为1。

设置比特位
1）将比特位设置为1
将第28个比特位设置为1：
只需将 1 左移（31-28）位数，然后与原来的值进行或运算。

2）将比特位设置为0
将第28个比特位设置为0：
只需将 1 左移（31-28）位数，并进行非运算，然后与原来的值进行与运算。

3）判断某一元素是否存在
判断 28 位比特位是否有元素存在：
只需将 1 左移（31-28）位数，然后与原来的值进行与运算。只要与运算结果中有1，即表示元素存在。所以可以用运行结果是不为0作为元素是否存在依据。

实现
public class BigMapTest {

    private int[] bigArray;

    public BigMapTest(long  size){
        bigArray = new int[(int) (size/ 32 + 1)];
    }

    public void set1(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;
        //设置0
        bigArray[arrayIndex] |= 1 << bitIndex;
    }

    public void set0(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;
        //设置0
        bigArray[arrayIndex] &= ~(1 << bitIndex);
        System.out.println(get32BitBinString(bigArray[arrayIndex]));
    }

    public boolean isExist(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;

        //判断是否存在
        return (bigArray[arrayIndex] & ((1 << bitIndex)))!=0 ? true : false;
    }

    /**
     * 将整型数字转换为二进制字符串，一共32位，不舍弃前面的0
     * @param number 整型数字
     * @return 二进制字符串
     */
    private static String get32BitBinString(int number) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 32; i++){
            sBuilder.append(number & 1);
            number = number >>> 1;
        }
        return sBuilder.reverse().toString();
    }
    
    public static void main(String[] args) {

        int[] arrays = new int[]{1, 2, 35, 22, 56, 334, 245, 2234, 54};

        BigMapTest bigMapTest = new BigMapTest(2234-1);

        for (int i : arrays) {
            bigMapTest.set1(i);
        }
        System.out.println(bigMapTest.isExist(35));
    }
}

BitMap 优缺点
优点：实现简单。适合数据量比较大的场景。
缺点：占用内存。申请的数组长度不好控制和最大的数值有关。当某个值特别大的情况下，映射的数组超过申请的数组容量，会出现下标越界。这也是上面提到的10亿个元素占用120M是最小内存的原因，实际可能会大于这个内存。
————————————————
版权声明：本文为CSDN博主「张书康」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/woshilijiuyi/article/details/88778214
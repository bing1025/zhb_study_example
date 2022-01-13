package com.example.zhb.study.demo.day5.deepCopy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhouhb
 * @date: 2021/09/27/15:20
 * @Description:
 */
public class DeepCopyUtil {

    /***
     * 方法一对集合进行深拷贝 注意须要对泛型类进行序列化(实现Serializable)
     *
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T extends Serializable> List<T> deepCopy1(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 方法二
     * 须要Address实现cloneable接口和重写clone方法，此方法有限制性，
     * 例如要先声明List是保存的什么对象，而且当碰到对象里面还持有List集合的时候
     * 就无法用的，因此建议使用第一种方法
     *
     * @param addresses
     * @return
     */
    public static List<Address> deepCopy2(List<Address> addresses) {
        List<Address> destList = new ArrayList<>();
        for (Address address : addresses) {
            destList.add((Address) address.clone());
        }
        return destList;
    }


}

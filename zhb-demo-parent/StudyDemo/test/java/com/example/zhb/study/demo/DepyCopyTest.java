package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day5.deepCopy.Address;
import com.example.zhb.study.demo.day5.deepCopy.DeepCopyUtil;
import com.example.zhb.study.demo.day5.deepCopy.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 比较器的用法
 * @Author: zhouhb
 * @date: 2021/09/24/15:04
 * @Description:
 */
public class DepyCopyTest {

    @Test
    public void test1(){
        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address("北京市"));
        Student student = new Student("zhb");
        student.addressList = addressList;

        Student student2 = (Student) student.clone();
        student2.addressList = DeepCopyUtil.deepCopy1(addressList);

        Student student3 = (Student) student.clone();
        student3.addressList = DeepCopyUtil.deepCopy2(addressList);

        println(student.getName() + "---" + student.addressList.get(0).getAddress());
        println(student2.getName() + "---" + student2.addressList.get(0).getAddress());
        println(student3.getName() + "---" + student3.addressList.get(0).getAddress());
        println("改变student的姓名后--------------");
        student.setName("飞雪");
        student.addressList.get(0).setAddress("湖北省");
        println(student.getName() + "---" + student.addressList.get(0).getAddress());
        println(student2.getName() + "---" + student2.addressList.get(0).getAddress());
        println(student3.getName() + "---" + student3.addressList.get(0).getAddress());

    }

    public static void println(String str) {
        System.out.println(str);
    }

}

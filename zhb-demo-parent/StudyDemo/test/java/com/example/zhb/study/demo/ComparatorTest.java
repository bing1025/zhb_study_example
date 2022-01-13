package com.example.zhb.study.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.day4.comparator.Entity;
import com.example.zhb.study.demo.day4.comparator.EntityComparator;
import com.example.zhb.study.demo.day4.comparator.Sha256Util;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * 比较器的用法
 * @Author: zhouhb
 * @date: 2021/09/24/15:04
 * @Description:
 */
public class ComparatorTest {

    @Test
    public void test1(){
        List<Entity> list1 = Lists.newArrayList();
        Entity entity1 = new Entity(1,"a","aaa");
        Entity entity2 = new Entity(2,"b","bbb");
        Entity entity3 = new Entity(3,"c","ccc");
        list1.add(entity1);
        list1.add(entity2);
        list1.add(entity3);

        List<Entity> list2 = Lists.newArrayList();
        Entity entity11 = new Entity(2,"b","bbb");
        Entity entity22 = new Entity(1,"a","aaa");
        Entity entity33 = new Entity(3,"c","ccc");
        list2.add(entity11);
        list2.add(entity22);
        list2.add(entity33);

        List<Entity> list3 = Lists.newArrayList();
        Entity entity111 = new Entity(2,"b1","bbb");
        Entity entity222 = new Entity(1,"a","aaa");
        Entity entity333 = new Entity(3,"c","ccc");
        list3.add(entity111);
        list3.add(entity222);
        list3.add(entity333);

        //调用方法进行排序
        Collections.sort(list1,new EntityComparator());
        String s1 = JSONObject.toJSONString(list1);
        String sha1 = Sha256Util.getSHA256(s1);

        //调用方法进行排序
        Collections.sort(list2,new EntityComparator());
        String s2 = JSONObject.toJSONString(list2);
        String sha2 = Sha256Util.getSHA256(s2);

        //调用方法进行排序
        Collections.sort(list3,new EntityComparator());
        String s3 = JSONObject.toJSONString(list3);
        String sha3 = Sha256Util.getSHA256(s3);

        System.out.println(sha1.equals(sha2));
        System.out.println(sha3.equals(sha2));

    }

}

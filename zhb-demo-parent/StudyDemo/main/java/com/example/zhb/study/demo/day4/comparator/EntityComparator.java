package com.example.zhb.study.demo.day4.comparator;

import java.util.Comparator;

/**
 * 自定义Entity的比较器
 * @Author: zhouhb
 * @date: 2021/09/24/14:59
 * @Description:
 */
public class EntityComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        int result = o1.getNum().compareTo(o2.getNum());
        if(result ==0){
            result = o1.getMsg().compareTo(o2.getMsg());
        }
        if(result ==0){
            result = o1.getRemark().compareTo(o2.getRemark());
        }
        return result;
    }
}

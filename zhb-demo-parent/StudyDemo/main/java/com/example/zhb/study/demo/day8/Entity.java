package com.example.zhb.study.demo.day8;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体类举例
 * @Author: zhouhb
 * @date: 2021/09/24/14:52
 * @Description:
 */
@Data
public class Entity implements Serializable {

    /**
     * 数字类型
     */
    private Integer num;

    /**
     * 字符类型1
     */
    private String msg;

    /**
     * 字符类型2
     */
    private String remark;

    public Entity() {
    }

    public Entity(Integer num, String msg, String remark) {
        this.num = num;
        this.msg = msg;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "num=" + num +
                ", msg='" + msg + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

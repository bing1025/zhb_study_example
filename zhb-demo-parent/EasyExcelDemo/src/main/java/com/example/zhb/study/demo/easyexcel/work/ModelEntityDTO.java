package com.example.zhb.study.demo.easyexcel.work;

import lombok.Data;

import java.io.Serializable;

/**
 * 这是业务一的实体解析类
 * @Author: zhouhb
 * @date: 2021/12/15/15:27
 * @Description:
 */
@Data
public class ModelEntityDTO implements Serializable {

    /**
     * 注意跟excel列的定义保持一致，另外初始解析进来的时候全部设置成String类型，后期转换
     */
    private String id;

    private String name;

    private String salary;

    private String birthday;
}

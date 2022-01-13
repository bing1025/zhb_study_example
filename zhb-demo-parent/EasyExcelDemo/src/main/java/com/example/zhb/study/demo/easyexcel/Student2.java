package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义表头
 * @Author: zhouhb
 * @date: 2021/11/09/17:19
 * @Description:
 */
public class Student2 implements Serializable {

    @ExcelProperty("学生编号")
    private Integer id;

    @ExcelProperty("学生姓名")
    private String name;

    @ExcelProperty("学生薪水")
    private Double salary;

    @ExcelProperty("学生生日")
    private Date birthday;
}

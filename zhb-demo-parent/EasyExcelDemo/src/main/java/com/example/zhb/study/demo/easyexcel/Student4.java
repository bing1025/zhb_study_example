package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 有时候更多的时候需要在表头上，在加上一个表头，例如为学生信息
 * 修改POJO类如下：
 * @Author: zhouhb
 * @date: 2021/11/09/17:19
 * @Description:
 */
@Data
public class Student4 implements Serializable {

    @ExcelProperty(value = {"学生信息","学生编号"},order = 10)
    private Integer id;

    @ExcelProperty(value = {"学生信息","学生姓名"},order = 2)
    private String name;

    @ExcelProperty(value = {"学生信息","学生薪水"},order = 1)
    private Double salary;

    @ExcelProperty(value = {"学生信息","学生生日"},order = 11)
    private Date birthday;
}

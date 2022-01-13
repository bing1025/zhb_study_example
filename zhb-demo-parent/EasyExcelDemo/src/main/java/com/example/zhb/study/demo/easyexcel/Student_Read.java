package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Student_Read implements Serializable {

    @ExcelProperty(value = {"学生信息","学生编号"})
    private Integer id;

    @ExcelProperty(value = {"学生信息","学生姓名"})
    private String name;

    @ExcelProperty(value = {"学生信息","学生薪水"})
    private Double salary;

    @ExcelProperty(value = {"学生信息","学生生日"})
    private Date birthday;
}

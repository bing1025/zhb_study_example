package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 如果想要自定义列的顺序时，可以修改POJO,如下
 * @Author: zhouhb
 * @date: 2021/11/09/17:19
 * @Description:
 */
@Data
public class Student3 implements Serializable {

    @ExcelProperty(value = "学生编号",order = 10)
    private Integer id;

    @ExcelProperty(value = "学生姓名",order = 2)
    private String name;

    @ExcelProperty(value = "学生薪水",order = 1)
    private Double salary;

    @ExcelProperty(value = "学生生日",order = 11)
    private Date birthday;
}

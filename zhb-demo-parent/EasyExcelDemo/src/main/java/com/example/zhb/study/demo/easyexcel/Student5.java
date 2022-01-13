package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 列宽，行高定义
 * 从上述例子可知，之前操作，产生的列的宽度与内容的宽度并没有对应,所以需要手动指定宽度，修改POJO类如下：
 * @Author: zhouhb
 * @date: 2021/11/09/17:19
 * @Description:
 */
@HeadRowHeight(value = 35) // 表头行高
@ContentRowHeight(value = 25) // 内容行高
@ColumnWidth(value = 50) // 列宽
@Data
public class Student5 implements Serializable {

    @ExcelProperty(value = {"学生信息","学生编号"},order = 10)
    private Integer id;

    @ExcelProperty(value = {"学生信息","学生姓名"},order = 2)
    private String name;

    @ExcelProperty(value = {"学生信息","学生薪水"},order = 1)
    private Double salary;

    @ExcelProperty(value = {"学生信息","学生生日"},order = 11)
    private Date birthday;
}

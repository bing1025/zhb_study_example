package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期格式化
 * 从上述例子中，发现日期格式都是固定的格式，但是有时候需要自定义格式，因此可以修改POJO类达到以下效果，如下
 * 动态指定哪些输出，哪些不输出
 * @Author: zhouhb
 * @date: 2021/11/09/17:19
 * @Description:
 */
@HeadRowHeight(value = 35) // 表头行高
@ContentRowHeight(value = 25) // 内容行高
@ColumnWidth(value = 50) // 列宽
@Data
public class Student6 implements Serializable {

    @ExcelProperty(value = {"学生信息","学生编号"},order = 10)
    private Integer id;

    @ExcelProperty(value = {"学生信息","学生姓名"},order = 2)
    private String name;

    @ExcelProperty(value = {"学生信息","学生薪水"},order = 1)
    private Double salary;

    @ExcelProperty(value = {"学生信息","学生生日"},order = 11)
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;
}

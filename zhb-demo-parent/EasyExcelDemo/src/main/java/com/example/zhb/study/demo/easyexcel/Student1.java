package com.example.zhb.study.demo.easyexcel;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Student1 implements Serializable {
    private Integer id;
    private String name;
    private Double salary;
    private Date birthday;
}
package com.example.zhb.study.demo.day28;

import lombok.Data;

/**
 * @Author: zhouhb
 * @date: 2023/09/11/10:33
 * @Description:
 */
@Data
public class ChildDTOB extends ParentDTO{
    private String city;

    @Override
    public String toString() {
        return "ChildDTOB{" +
                "city='" + city + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

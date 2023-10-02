package com.example.zhb.study.demo.day28;

import lombok.Data;

/**
 * @Author: zhouhb
 * @date: 2023/09/11/10:33
 * @Description:
 */
@Data
public class ChildDTOA extends ParentDTO{
    private String school;

    @Override
    public String toString() {
        return "ChildDTOA{" +
                "school='" + school + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

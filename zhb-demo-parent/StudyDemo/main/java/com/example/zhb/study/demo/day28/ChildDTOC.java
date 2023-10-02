package com.example.zhb.study.demo.day28;

import lombok.Data;

/**
 * @Author: zhouhb
 * @date: 2023/09/11/10:33
 * @Description:
 */
@Data
public class ChildDTOC extends ParentDTO{
    private String home;

    @Override
    public String toString() {
        return "ChildDTOC{" +
                "home='" + home + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

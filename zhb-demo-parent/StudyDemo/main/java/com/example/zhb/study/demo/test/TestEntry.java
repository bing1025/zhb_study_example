package com.example.zhb.study.demo.test;

import lombok.Data;

/**
 * @Author: zhouhb
 * @date: 2022/05/18/16:12
 * @Description:
 */
@Data
public class TestEntry {

    private Integer id;

    private String numStr;

    public TestEntry(Integer id, String numStr) {
        this.id = id;
        this.numStr = numStr;
    }
}

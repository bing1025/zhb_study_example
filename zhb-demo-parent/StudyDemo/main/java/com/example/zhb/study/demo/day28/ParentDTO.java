package com.example.zhb.study.demo.day28;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhouhb
 * @date: 2023/09/11/10:32
 * @Description:
 */
@Data
public abstract class ParentDTO implements Serializable {

    String name;
}

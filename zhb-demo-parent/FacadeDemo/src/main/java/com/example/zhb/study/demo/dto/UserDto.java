package com.example.zhb.study.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhouhb
 * @date: 2021/12/31/11:21
 * @Description:
 */
@Data
public class UserDto implements Serializable {

    private Integer id;
    private String userName;
    private String passWord;
    private String realName;
}

package com.example.zhb.study.demo.service;

import com.example.zhb.study.demo.dto.UserDto;

/**
 * @Author: zhouhb
 * @date: 2021/12/31/11:20
 * @Description:
 */
public interface UserService {

    public UserDto SelectById(int id);
}

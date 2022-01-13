package com.example.zhb.study.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.zhb.study.demo.bean.User;
import com.example.zhb.study.demo.dto.UserDto;
import com.example.zhb.study.demo.mapper.UserMapper;
import com.example.zhb.study.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zhouhb
 * @date: 2021/12/31/11:24
 * @Description:
 */
@Service  // import com.alibaba.dubbo.config.annotation.Service;
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto SelectById(int id) {
        User user = userMapper.SelectById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }
}

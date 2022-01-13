package com.example.zhb.study.demo.mapper;

import com.example.zhb.study.demo.bean.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhouhb
 * @date: 2021/12/31/11:24
 * @Description:
 */
@Repository
public interface UserMapper extends BaseMapper{

    User SelectById(int id);
}

package com.example.zhb.study.demo.mapper;

import com.example.zhb.study.demo.bean.SqlLog;
import com.example.zhb.study.demo.exception.BaseRuntimeException;

/**
 * 封装基本的mapper接口
 * 例如增删查改等常用方法
 * 这里演示的是记录 com.example.zhb.study.demo.utils.mybatis.MybatisInterceptor
 * 里面的 巧用  MyBatis 的 拦截器 往日志表中插入记录的方法，假设方法为id="insertSqlLog"
 * @Author: zhouhb
 * @date: 2021/12/31/17:35
 * @Description:
 */
public interface BaseMapper {

    /**
     * 强制要求子类必须实现
     * @param log
     * @return
     */
    default Integer insertSqlLog(SqlLog log) {
      throw new BaseRuntimeException("子类没有实现 insertSqlLog 方法，没法往 日志表中插入操作记录 ");
    }
}

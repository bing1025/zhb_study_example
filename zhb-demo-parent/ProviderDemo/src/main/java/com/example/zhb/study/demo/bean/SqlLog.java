package com.example.zhb.study.demo.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhouhb
 * @date: 2021/12/31/17:17
 * @Description:
 */
@Data
public class SqlLog {

    /**
     * 记录SQL
     */
    private String sqlClause;
    /**
     * 记录影响行数
     */
    private Integer result;
    /**
     * 记录时间
     */
    private Date whenCreated;
}

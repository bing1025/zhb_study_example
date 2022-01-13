package com.example.zhb.study.demo.message.support;

import lombok.Data;

import java.io.Serializable;

/***
 *
 * @author Xiaojie.Li
 * @description source
 * @date 2021/5/31
 */
@Data
public class MessageSource implements Serializable {

    private String loginUserPhone;
    private Long loginUserId;

    public MessageSource(String loginUserPhone,Long loginUserId) {
        this.loginUserPhone = loginUserPhone;
        this.loginUserId = loginUserId;
    }
}

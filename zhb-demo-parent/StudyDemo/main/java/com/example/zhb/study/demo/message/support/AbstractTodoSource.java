package com.example.zhb.study.demo.message.support;

/***
 *
 * @author Xiaojie.Li
 * @description 基础执行器
 * @date 2021/5/31
 */
public abstract class AbstractTodoSource extends MessageSource{

    // private MessageTypeEnum messageTypeEnum;

    public AbstractTodoSource(String loginUserPhone,Long loginUserId){
        super(loginUserPhone,loginUserId);
    }

}

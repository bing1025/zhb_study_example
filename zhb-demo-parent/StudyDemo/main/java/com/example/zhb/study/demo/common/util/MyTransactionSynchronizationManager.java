package com.example.zhb.study.demo.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * TransactionSynchronizationManager afterCommit 事务执行之后执行
 * TransactionSynchronizationManager
 *
 * 是一个事务管理的核心类，通过TransactionSynchronizationManager我们可以管理当前线程的事务。
 * 而很多时候我们使用这个类是为了方便我们在事务结束或者开始之前实现一些自己的逻辑
 * @Author: zhouhb
 * @date: 2021/07/05/20:17
 * @Description:
 */
@Slf4j
public class MyTransactionSynchronizationManager {
    public void add() {
        log.info("任务开始！");
        /*TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                // doSomething
            }
        });*/
        log.info("任务已经结束！");
    }
}

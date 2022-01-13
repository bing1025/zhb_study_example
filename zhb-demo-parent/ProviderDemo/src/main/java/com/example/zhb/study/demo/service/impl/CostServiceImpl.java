package com.example.zhb.study.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.zhb.study.demo.service.CostService;
import lombok.extern.slf4j.Slf4j;


/**
 * 花费服务
 */
//@Service(filter = {"providerRpcTraceFilter"})  // 日志跟踪
@Service
@Slf4j
public class CostServiceImpl implements CostService {

    /**
     * 假设之前总花费了100
     */
    private final Integer totalCost = 1000;

    /**
     * 之前总和 加上 最近一笔
     * @param cost
     * @return
     */
    @Override
    public Integer add(int cost) {
        log.info("生产者带着traceId 返回了");
        return totalCost + cost;
    }
}


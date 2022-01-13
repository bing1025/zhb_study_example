package com.example.zhb.study.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.zhb.study.demo.common.response.RestResponse;
import com.example.zhb.study.demo.service.CostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品controller
 */
@RestController
@Slf4j
public class ProductController {


    /**
     * 使用dubbo的注解 com.alibaba.dubbo.config.annotation.Reference。进行远程调用service
     *
     * consumerRpcTraceFilter 日志跟踪尝试
     */
    //@Reference(filter = {"consumerRpcTraceFilter"})
    @Reference
    private CostService costService;

    /**
     * 添加完 返回总共消费
     * @param a
     * @return
     */
    @RequestMapping("/add")
    public RestResponse<String> getCost(int a){
        Integer cost = costService.add(a);
        log.info("消费者带着traceId 返回了");
        return RestResponse.ok("该产品总共消费 ："+cost);
    }
}


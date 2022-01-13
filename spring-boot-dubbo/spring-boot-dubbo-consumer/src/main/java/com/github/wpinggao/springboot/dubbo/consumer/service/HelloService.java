package com.github.wpinggao.springboot.dubbo.consumer.service;

import com.github.wpinggao.springboot.dubbo.api.service.HelloDubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2020/3/28 11:12
 */
@Service
public class HelloService {

    @Reference(version = "1.0.0")
    private HelloDubboService helloDubboService;

    public String hello(String name){
        return helloDubboService.hello(name);
    }

}

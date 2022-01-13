package com.github.wpinggao.springboot.dubbo.consumer.service;

import com.github.wpinggao.springboot.dubbo.api.service.EchoDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EchoService {

    @Autowired
    private EchoDubboService echoDubboService;

    public String userEcho(String name, Integer num){

        String result = echoDubboService.echo(name, num);

        return result;
    }

}

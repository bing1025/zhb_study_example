package com.github.wpinggao.springboot.dubbo.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("echoCallBackService")
public class EchoCallBackService {

    private static final Logger logger = LoggerFactory.getLogger(EchoCallBackService.class);

    public void oninvoke(String name, Integer num){
        logger.info("========oninvoke, name:{}", name);
    }

    public void onreturn(String result, String name, Integer num){
        logger.info("--------onreturn, result:{}, name:{}", result, name);
    }

    public void onthrow(Throwable ex, String name, Integer num){

        logger.error("--------onthrow, name:{}", name, ex);

    }


}

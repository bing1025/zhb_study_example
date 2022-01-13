package com.github.wpinggao.springboot.dubbo.provider.service.dubbo;

import com.github.wpinggao.springboot.dubbo.api.service.EchoDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("echoDubboService")
public class EchoDubboServiceImpl implements EchoDubboService {

    private static final Logger logger = LoggerFactory.getLogger(EchoDubboServiceImpl.class);

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public String echo(String name, Integer num) {

        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("========================echo:{}", env + "-" + name);
        return env + "-" + name;
    }
}

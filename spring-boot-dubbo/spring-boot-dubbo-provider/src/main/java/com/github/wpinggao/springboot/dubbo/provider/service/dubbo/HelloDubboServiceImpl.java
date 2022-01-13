package com.github.wpinggao.springboot.dubbo.provider.service.dubbo;

import com.github.wpinggao.springboot.dubbo.api.service.HelloDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2020/3/28 11:11
 */
@Service(version = "1.0.0")
@Component
@Slf4j
public class HelloDubboServiceImpl implements HelloDubboService {
    @Override
    public String hello(String name) {
        log.info("生产者返回");
        return "hello " + name;
    }
}

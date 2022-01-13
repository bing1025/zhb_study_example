package com.example.zhb.study.demo.day15.initEnum;

import com.example.zhb.study.demo.day15.TestExtEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationInit implements ApplicationListener<ApplicationReadyEvent> {
    static Class[] classes ={
            TestExtEnum.class
    };
    
    // 项目启动后预热
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("ApplicationInit start ");
        EnumInitializer.initEnums(classes);
        log.info("ApplicationInit end ");
    }
}
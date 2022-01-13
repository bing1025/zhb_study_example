package com.example.zhb.study.demo.day9.test.mock;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;


/**
 * @Author: zhouhb
 * @date: 2021/10/26/9:33
 * @Description:
 */
@Slf4j
@Service
public class OutImplement implements OutInterface{

    @Reference(url = "dubbo://127.0.0.1:12345",validation = "true",timeout = 50000)
    private OutInterface outInterface;

    @Override
    public String work() {
        log.debug("假装这个是外部接口的实现类，这里就不配置dubbo外部接口了");
        outInterface.work();
        return "假装这个是外部接口的实现类，这里就不配置dubbo外部接口了";
    }
}

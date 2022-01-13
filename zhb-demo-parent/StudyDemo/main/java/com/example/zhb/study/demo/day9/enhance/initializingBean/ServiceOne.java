package com.example.zhb.study.demo.day9.enhance.initializingBean;

import com.example.zhb.study.demo.day3.initializingBeanDemo.ServiceInterface;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * 接口实现的第一个场景分支
 * @Author: zhouhb
 * @date: 2021/09/24/10:51
 * @Description:
 */
@Log
@Service("serviceOne9")
public class ServiceOne implements ServiceInterface {
    @Override
    public String version() {
        return "one-version";
    }

    @Override
    public void test() {
       log.info("这是走的one-version");
    }
}

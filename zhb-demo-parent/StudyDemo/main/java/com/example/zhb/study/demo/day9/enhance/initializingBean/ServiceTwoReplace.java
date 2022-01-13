package com.example.zhb.study.demo.day9.enhance.initializingBean;

import com.example.zhb.study.demo.day9.enhance.anno.ExtReplaceBean;
import lombok.extern.java.Log;

/**
 * 接口实现的第二个场景分支
 * @Author: zhouhb
 * @date: 2021/09/24/10:51
 * @Description:
 */
@Log
@ExtReplaceBean(classTypes={ServiceTwo.class})
public class ServiceTwoReplace extends ServiceTwo {
    @Override
    public String version() {
        return "two-version Replace";
    }

    @Override
    public void test() {
       log.info("这是走的two-version Replace ");
    }
}

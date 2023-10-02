package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/18:04
 * @Description:
 */
@Component
public class Service2 implements ServiceInterFace<ChildDTOB>{

    @Override
    public int getOrder(ChildDTOB childDTOB) {
        System.out.println("====="+childDTOB);
        return 0;
    }
}

package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/18:04
 * @Description:
 */
@Component
public class Service1 implements ServiceInterFace<ChildDTOA>{

    @Override
    public int getOrder(ChildDTOA childDTOA) {
        System.out.println("====="+childDTOA);
        return 0;
    }
}

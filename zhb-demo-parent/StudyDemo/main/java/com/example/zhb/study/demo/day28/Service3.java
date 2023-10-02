package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/18:04
 * @Description:
 */
@Component
public class Service3 implements ServiceInterFace<ChildDTOC>{

    @Override
    public int getOrder(ChildDTOC childDTOC) {
        System.out.println("====="+childDTOC);
        return 0;
    }
}

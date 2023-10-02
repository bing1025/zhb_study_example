package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/17:58
 * @Description:
 */
@CustomAnnotation(order = 2, groups = "zhbB")
@Component
public class ServiceB extends Service2 {

    @Override
    public int getOrder(ChildDTOB childDTOB) {
        System.out.println("====="+childDTOB);
        return 1;
    }

}

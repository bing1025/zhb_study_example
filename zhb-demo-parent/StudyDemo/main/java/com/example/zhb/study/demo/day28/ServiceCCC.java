package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/17:58
 * @Description:
 */
@CustomAnnotation(order = -1, groups = "zhbC")
@Component
public class ServiceCCC extends Service3 {

    @Override
    public int getOrder(ChildDTOC childDTOC) {
        System.out.println("====="+childDTOC);
        return -1;
    }

}

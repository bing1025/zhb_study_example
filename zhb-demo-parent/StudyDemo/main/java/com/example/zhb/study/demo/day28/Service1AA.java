package com.example.zhb.study.demo.day28;

import org.springframework.stereotype.Component;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/17:58
 * @Description:
 */
@CustomAnnotation(order = 11, groups = "zhbA")
@Component
public class Service1AA extends Service1 {

    @Override
    public int getOrder(ChildDTOA childDTOA) {
        System.out.println("====="+childDTOA);
        return 11;
    }

}

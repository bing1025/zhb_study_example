package com.example.zhb.study.demo.day28;

/**
 * @Author: zhouhb
 * @date: 2023/09/09/18:05
 * @Description:
 */
public interface ServiceInterFace<T extends ParentDTO> {

    int getOrder(T parentDTO);
}

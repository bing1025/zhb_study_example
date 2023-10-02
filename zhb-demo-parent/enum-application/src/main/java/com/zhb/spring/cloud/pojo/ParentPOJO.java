package com.zhb.spring.cloud.pojo;

import lombok.Data;

/**
 * @Author: zhouhb
 * @date: 2023/06/15/9:34
 * @Description:
 */
@Data
public class ParentPOJO {
    // 父类属性值
    private String parentProperty;

    // 分组标志
    private String group;

    // 判断标志
    private String adapterStr;

}

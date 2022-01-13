package com.example.zhb.study.demo.easyexcel.work;

import lombok.Data;

import java.util.List;

/**
 * ModelEntity 在 跟listen交互时候出入参数，放这里面，方便处理
 * @Author: zhouhb
 * @date: 2021/12/15/16:18
 * @Description:
 */
@Data
public class ModelEntityReqAndResDTO {

    /**
     * 收集所有的excel解析后的实体类集合
     */
    private List<ModelEntityDTO>  modelEntityDTOList;
}

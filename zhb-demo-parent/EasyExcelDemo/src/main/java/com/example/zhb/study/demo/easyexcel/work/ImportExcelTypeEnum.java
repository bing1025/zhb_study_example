package com.example.zhb.study.demo.easyexcel.work;

import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @Author: zhouhb
 * @date: 2021/12/15/15:17
 * @Description:
 */
public enum ImportExcelTypeEnum {
    Model_ONE("Model_ONE","这个是业务一",ImportExcelTypeConstant.MODEL_ONE_HEADCOUNT,ModelEntityDTOListener.class,ModelEntityDTO.class,ImportExcelTypeConstant.MODEL_ONE_sheetNumber);

    /**
     * 业务模块的定义
     */
    private String type;

    /**
     * 业务定义的描述
     */
    private String desc;

    /**
     * 从第几行开始解析数据
     */
    private Integer headCount;

    /**
     * 解析数据具体的实现类
     */
    private Class<? extends AnalysisEventListener> listenerClass;

    /**
     * 解析的数据实体类
     */
    private Class beanModel;

    /**
     * 第几个sheet页
     */
    private Integer sheetNumber;

    ImportExcelTypeEnum(String type, String desc, Integer headCount, Class<? extends AnalysisEventListener> listenerClass, Class beanModel, Integer sheetNumber) {
        this.type = type;
        this.desc = desc;
        this.headCount = headCount;
        this.listenerClass = listenerClass;
        this.beanModel = beanModel;
        this.sheetNumber = sheetNumber;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getHeadCount() {
        return headCount;
    }

    public Class<? extends AnalysisEventListener> getListenerClass() {
        return listenerClass;
    }

    public Class getBeanModel() {
        return beanModel;
    }

    public Integer getSheetNumber() {
        return sheetNumber;
    }
}

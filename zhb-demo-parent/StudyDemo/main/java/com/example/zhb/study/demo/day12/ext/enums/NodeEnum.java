package com.example.zhb.study.demo.day12.ext.enums;

/**
 * 流程引擎关键节点枚举
 * @Author: zhouhb
 * @date: 2021/11/06/14:58
 * @Description:
 */

public enum NodeEnum {

    NODE1("node1","第一个关键节点,触发的是要做的第一件事",""),
    NODE2("node2","第二个关键节点,触发的是要做的第二件事",""),
    NODE3("node3","第三个关键节点,触发的是要做的第三件事","")
    ;


    /**
     * 页面步骤事件编码
     */
    private String nodeCode;

    /**
     * 页面步骤事件描述
     */
    private String nodeDesc;

    /**
     * 页面步骤类型
     * 目前没用，以后扩展
     */
    private String nodeType;

    NodeEnum(String nodeCode, String nodeDesc, String nodeType) {
        this.nodeCode = nodeCode;
        this.nodeDesc = nodeDesc;
        this.nodeType = nodeType;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public String getNodeType() {
        return nodeType;
    }
}

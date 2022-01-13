package com.example.zhb.study.demo.day8;

/**
 * 实体类举例 继承举例
 * @Author: zhouhb
 * @date: 2021/10/25/14:04
 * @Description:
 */
public class EntityImplement extends Entity {

    /**
     * 字符类型1
     */
    private String msgExt;

    /**
     * 字符类型2
     */
    private String remarkExt;

    public String getMsgExt() {
        return getMsg();
    }

    public void setMsgExt(String msgExt) {
        this.msgExt = msgExt;
        setMsg(msgExt);
    }

    public String getRemarkExt() {
        return getRemark();
    }

    public void setRemarkExt(String remarkExt) {
        this.remarkExt = remarkExt;
        setRemark(remarkExt);
    }
}

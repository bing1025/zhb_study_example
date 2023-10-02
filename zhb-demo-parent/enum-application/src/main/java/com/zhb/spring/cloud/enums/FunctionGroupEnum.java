package com.zhb.spring.cloud.enums;

import com.zhb.spring.cloud.pojo.ZHBPOJO;
import com.zhb.spring.cloud.util.ParamsReflect;

import java.util.function.Function;

/**
 * 枚举按照group分组，然后每一个组判断满足条件后固定执行指定的事项
 * @Author: zhouhb
 * @date: 2023/06/14/20:39
 * @Description:
 */
public enum FunctionGroupEnum {
    ZHB_1("zhb1","规则1","zhb1",
      (str)->{
        return str.equals("zhb1");
    },(str)->{
        return str+"已经 execute 了";
    },"name"),
    ZHB_11("zhb11","规则1扩展","zhb1",
            (str)->{
                return str.equals("zhb11");
            },(str)->{
        return str+"已经 execute 了";
    },"name1"),
    ZHB_2("zhb2","规则2","zhb2",
            (str)->{
                return str.equals("zhb2");
            },(str)->{
        return str+"已经 execute 了";
    },"home"),
    ZHB_22("zhb22","规则2","zhb2",
            (str)->{
                return str.equals("zhb2");
            },(str)->{
        return str+"已经 execute 了";
    },"home1"),
    ZHB_3("zhb3","规则3","zhb3",
            (str)->{
                return str.equals("zhb3");
            },(str)->{
        return str+"已经 execute 了";
    },"parentProperty"),
    ;

    private String code;
    private String desc;
    private String group;
    // 满足条件的使用场景
    private Function<String,Boolean> adapterHandle;
    // 执行的后续动作
    private Function<String,String> executeHandle;
    // 获取上下文实体的属性值
    private String property;

    FunctionGroupEnum(String code, String desc, String group, Function<String, Boolean> adapterHandle, Function<String, String> executeHandle, String property) {
        this.code = code;
        this.desc = desc;
        this.group = group;
        this.adapterHandle = adapterHandle;
        this.executeHandle = executeHandle;
        this.property = property;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getGroup() {
        return group;
    }

    public Function<String, Boolean> getAdapterHandle() {
        return adapterHandle;
    }

    public Function<String, String> getExecuteHandle() {
        return executeHandle;
    }

    public String getProperty() {
        return property;
    }

    /**
     * 判断是否满足规则的条件
     */
    public Boolean checkAdapter(String condition){
        return adapterHandle.apply(condition);
    }

    /**
     * 调用执行的方法
     */
    public String execute(String condition){
        return executeHandle.apply(condition);
    }


    /**
     * 根据分组执行满足条件的结果
     * @param zhbpojo
     * @return
     */
    public static String getResultByGroup(ZHBPOJO zhbpojo){
        if(null == zhbpojo){
            return "";
        }
        // 强制更改下属性值
        // ParamsReflect.setMethodValueByClass(zhbpojo,"parentProperty",zhbpojo.getParentProperty()+"_replace");

        for (FunctionGroupEnum item : FunctionGroupEnum.values()) {
            if(item.getGroup().equals(zhbpojo.getGroup())){
                if(item.checkAdapter(zhbpojo.getAdapterStr())){
                    String methodValueByClass = ParamsReflect.getMethodValueByClass(zhbpojo, item.getProperty());

                    System.out.println("====="+item.execute(methodValueByClass));
                }
            }

        }
        return "success";
    }

    public static void main(String[] args) {

        System.out.println("=====1=======");
        ZHBPOJO zhbpojo1 = new ZHBPOJO();
        zhbpojo1.setParentProperty("zhb-1");
        zhbpojo1.setGroup("zhb1");
        zhbpojo1.setAdapterStr("zhb1");
        zhbpojo1.setName("1");
        zhbpojo1.setName1("1-1");
        zhbpojo1.setHome("1-11");
        zhbpojo1.setHome1("1-11-11");
        getResultByGroup(zhbpojo1);

        System.out.println("=====2=======");
        ZHBPOJO zhbpojo11 = new ZHBPOJO();
        zhbpojo11.setParentProperty("zhb-11");
        zhbpojo11.setGroup("zhb1");
        zhbpojo11.setAdapterStr("zhb11");
        zhbpojo11.setName("11");
        zhbpojo11.setName1("11-1");
        zhbpojo11.setHome("11-11");
        zhbpojo11.setHome1("11-11-11");
        getResultByGroup(zhbpojo11);

        System.out.println("=====3=======");
        ZHBPOJO zhbpojo2 = new ZHBPOJO();
        zhbpojo2.setParentProperty("zhb-2");
        zhbpojo2.setGroup("zhb2");
        zhbpojo2.setAdapterStr("zhb2");
        zhbpojo2.setName("2");
        zhbpojo2.setName1("2-1");
        zhbpojo2.setHome("2-11");
        zhbpojo2.setHome1("2-11-11");

        getResultByGroup(zhbpojo2);


        System.out.println("=====4=======");
        ZHBPOJO zhbpojo222 = new ZHBPOJO();
        zhbpojo222.setParentProperty("zhb-222");
        zhbpojo222.setGroup("zhb2");
        zhbpojo222.setAdapterStr("zhb222");
        zhbpojo222.setName("22");
        zhbpojo222.setName1("22-1");
        zhbpojo222.setHome("22-11");
        zhbpojo222.setHome1("22-11-11");
        getResultByGroup(zhbpojo222);


        System.out.println("=====5=======");
        ZHBPOJO zhbpojo3 = new ZHBPOJO();
        zhbpojo3.setParentProperty("zhb-3");
        zhbpojo3.setGroup("zhb3");
        zhbpojo3.setAdapterStr("zhb3");
        zhbpojo3.setName("3");
        zhbpojo3.setName1("3-1");
        zhbpojo3.setHome("3-11");
        zhbpojo3.setHome1("3-11-11");
        getResultByGroup(zhbpojo3);
    }


}

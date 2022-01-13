package com.example.zhb.study.demo.day15;

import com.example.zhb.study.demo.day15.util.EnumBuster;

/**
 * TestEnum 的扩展
 * @Author: zhouhb
 * @date: 2021/11/18/17:49
 * @Description:
 */
public class TestExtEnum {

    static{
        EnumBuster<TestEnum> enumBuster = new EnumBuster<>(TestEnum.class);

        Object[][]  oldValues = {
                {"STEP1","step1","第一步要做的事情","这是本身源码有的"},
                {"STEP2","step2","第二步要做的事情","这是本身源码有的"},

        };

        Object[][]  newValues = {
                {"STEP4","step4","第四步要做的事情","这是扩展出来的"},
                {"STEP5","step5","第五步要做的事情","这是扩展出来的"},
                {"STEP6","step6","第六步要做的事情","这是扩展出来的"},
        };


        for (Object[] oldValue : oldValues) {
            TestEnum value = enumBuster.make((String)oldValue[0],2,
                    new Class[]{String.class,String.class,String.class},
                    new Object[]{oldValue[1],oldValue[2],oldValue[3]});
            enumBuster.deleteByValue(value);
        }

        for (Object[] newValue : newValues) {
            TestEnum value = enumBuster.make((String)newValue[0],2,
                    new Class[]{String.class,String.class,String.class},
                    new Object[]{newValue[1],newValue[2],newValue[3]});
            enumBuster.addByValue(value);
        }

    }

}

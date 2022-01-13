package com.example.zhb.study.demo.day15.initEnum;

import com.example.zhb.study.demo.day15.TestEnum;
import com.example.zhb.study.demo.day15.TestExtEnum;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * ApplicationContextInitializer使用以及加载的原理
 * https://www.cnblogs.com/hello-shf/p/10987360.html
 *
 * 这个方法发现更改配置文件和 spring.factories 都没效果，测试类也没效果，改成不用 ApplicationContextInitializer 方法，
 * 换成 项目启动后执行的某种方式 ApplicationInit 来试试
 *
 * 扩展的枚举启动时候指定加载
 * @Author: zhouhb
 * @date: 2021/11/18/18:03
 * @Description:
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EnumExtInit implements ApplicationContextInitializer {

    static Class[] classes ={
            TestExtEnum.class
    };

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        EnumInitializer.initEnums(classes);
    }

    public static void main(String[] args) {
        for (TestEnum value : TestEnum.values()) {
            System.out.println("===="+value.getActCode());
        }
    }
}

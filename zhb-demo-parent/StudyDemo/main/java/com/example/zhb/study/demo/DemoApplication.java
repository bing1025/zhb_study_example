package com.example.zhb.study.demo;

import com.example.zhb.study.demo.day15.TestEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

         SpringApplication.run(DemoApplication.class, args);
        // ApplicationContextInitializer 三种实现方式其一  https://www.cnblogs.com/hello-shf/p/10987360.html
        // 三种实现方式其二,其三发现有问题，没生效，只有方法一生效，另外test测试类也没效果
        /*SpringApplication application = new SpringApplication(DemoApplication.class);
        application.addInitializers(new EnumExtInit());
        application.run(args);*/

        // 打印 step3 -- step6 说明项目启动成功
        for (TestEnum value : TestEnum.values()) {
            System.out.println("===="+value.getActCode());
        }
    }

}

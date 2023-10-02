package com.example.zhb.study.demo.day28;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CustomAnnotation {
    int order() default 0;
    String[] groups() ;
}

//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.TYPE,ElementType.METHOD})
//@Documented
//public @interface Strategy {
//    String[] names();
//}
//————————————————
//        版权声明：本文为CSDN博主「qq_26264237」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_26264237/article/details/103108927
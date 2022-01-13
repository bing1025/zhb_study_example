package com.example.zhb.study.demo.utils.mybatis;

import org.apache.ibatis.plugin.Signature;
import java.lang.annotation.*;

/**
 * Mybatis拦截器
 * https://blog.csdn.net/wuyuxing24/article/details/89343951
 * @Intercepts注解
 *  Intercepts注解需要一个Signature(拦截点)参数数组。通过Signature来指定拦截哪个对象里面的哪个方法
 *
 *  Mybatis拦截器实例-自定义拦截器
 *        上面讲了一大堆，最终的目的都是要使用上拦截器，接下来。我们通过几个简单的自定义拦截器来加深对Mybatis拦截器的理解。实例代码在链接地址：https://github.com/tuacy/microservice-framework 的 mybatis-interceptor module里面。
 *
 * 3.1 日志打印
 *        自定义LogInterceptor拦截器，打印出我们每次sq执行对应sql语句。
 *
 * 3.2 分页
 *        模仿pagehelper，咱们也来实现一个分页的拦截器PageInterceptor，该拦截器也支持自定义count查询。
 *
 * 3.3 分表
 *        自定义拦截器TableShardInterceptor实现水平分表的功能。
 *
 * 3.4 对查询结果的某个字段加密
 *        自定义拦截器EncryptResultFieldInterceptor对查询回来的结果中的某个字段进行加密处理。
 *
 * 上面拦截器的实现，在github https://github.com/tuacy/microservice-framework 的 mybatis-interceptor module里面都能找到具体的实现。
 * ————————————————
 * 版权声明：本文为CSDN博主「tuacy」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/wuyuxing24/article/details/89343951
 *
 * Intercepts 要用 org.apache.ibatis.plugin.Intercepts 这个里面的
 *
 * 下面是 Intercepts 的解释
 *
 */
/*@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    *//**
     * 定义拦截点
     * 只有符合拦截点的条件才会进入到拦截器
     *//*
    Signature[] value();
}*/

/*org.apache.ibatis.plugin.Signature来指定咱们需要拦截那个类对象的哪个方法。定义如下：
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Signature {
    *//**
     * 定义拦截的类 Executor、ParameterHandler、StatementHandler、ResultSetHandler当中的一个
     *//*
    Class<?> type();

    *//**
     * 在定义拦截类的基础之上，在定义拦截的方法
     *//*
    String method();

    *//**
     * 在定义拦截方法的基础之上在定义拦截的方法对应的参数，
     * JAVA里面方法可能重载，不指定参数，不晓得是那个方法
     *//*
    Class<?>[] args();
}*/


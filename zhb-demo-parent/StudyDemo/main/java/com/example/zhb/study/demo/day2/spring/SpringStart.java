package com.example.zhb.study.demo.day2.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring入口，方便调试
 * @Author: zhouhb
 * @date: 2021/06/25/19:42
 * @Description:
 */
@Slf4j
public class SpringStart {

    /**
     * https://www.cnblogs.com/tunsuy/p/14723549.html
     *
     * 不管是哪种系列的spring（springframework、springmvc、springboot、springcloud），Spring的启动过程主要可以分为两部分：
     *
     * 第一步：解析成BeanDefinition：将bean定义信息解析为BeanDefinition类，不管bean信息是定义在xml中，还是通过@Bean注解标注，都能通过不同的BeanDefinitionReader转为BeanDefinition类，将BeanDefinition向Map中注册 Map<name,beandefinition>。
     * 这里分两种BeanDefinition，RootBeanDefintion和BeanDefinition。RootBeanDefinition这种是系统级别的，是启动Spring必须加载的6个Bean。BeanDefinition是我们定义的Bean。
     * 第二步：参照BeanDefintion定义的类信息，通过BeanFactory生成bean实例存放在缓存中。
     * 这里的BeanFactoryPostProcessor是一个拦截器，在BeanDefinition实例化后，BeanFactory生成该Bean之前，可以对BeanDefinition进行修改。
     * BeanFactory根据BeanDefinition定义使用反射实例化Bean，实例化和初始化Bean的过程中就涉及到Bean的生命周期了，典型的问题就是Bean的循环依赖。接着，Bean实例化前会判断该Bean是否需要增强，并决定使用哪种代理来生成Bean。
     */

    public static void main(String[] args) {
        // 方法1 启动 AnnotationConfigApplicationContext 的无参构造器
        /*AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 不一定非要 @Configuration 注解的类
        applicationContext.register(BeanConfig.class);
        applicationContext.register(ServiceA.class);
        applicationContext.refresh();*/

        // 方法2 启动 AnnotationConfigApplicationContext 的有一个参数构造器，常用的是这个
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        Entitlement entitlement = (Entitlement) applicationContext.getBean("entitlement");
        log.info("entitlement ---{}",entitlement);

        // 测试下 beanPostProcessor 信息
      ServiceB serviceB = applicationContext.getBean(ServiceB.class);
        serviceB.testSayHello();

        //创建一个被代理者
       /*   ServiceB serviceB11 = new ServiceB();
        //创建一个InvocationHandler，描述我们希望代理者执行哪些操作
        MyProxy myProxy = new MyProxy();
        //代理对象的调用处理程序，我们将要代理的真实对象传入代理对象的调用处理的构造函数中，最终代理对象的调用处理程序会调用真实对象的方法
        myProxy.setObj(myProxy);
        myProxy.setName("serviceB11");
        //通过刚才创建的InvocationHandler，创建真正的代理者。第一个参数是类加载器，第二个参数是这个代理者实现哪些接口


        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
         * 第一个参数：people.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：people.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
        /*ServiceB proxy = (ServiceB) Proxy.newProxyInstance(myProxy.getClass().getClassLoader(), myProxy.getClass().getInterfaces(), myProxy);
        log.info("myProxy ---- proxy ---");
        proxy.testSayHello();*/




    }

    /**
     * 1: spring加载流程之AnnotatedBeanDefinitionReader
     * https://blog.csdn.net/yu_kang/article/details/88068619
     *
     * Spring的Bean加载流程
     * https://blog.csdn.net/a745233700/article/details/113840727
     *
     * spring--bean加载流程
     *https://blog.csdn.net/birdswillbecomdragon/article/details/105708423
     */

}

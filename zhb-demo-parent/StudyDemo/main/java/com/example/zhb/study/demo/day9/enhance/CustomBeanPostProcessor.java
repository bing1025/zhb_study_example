package com.example.zhb.study.demo.day9.enhance;

import com.example.zhb.study.demo.day9.enhance.anno.DubboMock;
import com.example.zhb.study.demo.day9.enhance.anno.ExtReplaceBean;
import com.example.zhb.study.demo.day9.enhance.anno.MyBeanDefinitionHandler;
import com.example.zhb.study.demo.day9.enhance.component.CustomBeanDefinitionCache;
import com.example.zhb.study.demo.day9.enhance.component.DubboMockConditional;
import com.example.zhb.study.demo.day9.enhance.handler.CustomBeanDefinitionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义的后置处理器
 * @Author: zhouhb
 * @date: 2021/10/25/15:12
 * @Description:
 */
@Slf4j
@Configuration
public class CustomBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    // @Value("${bool.replace.enabled}")  读取配置文件的内容
    private boolean boolReplaceEnabled;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 是否开启了自定义增强扩展
        /*if(!boolReplaceEnabled){
            return;
        }*/

        // 扫描自定义的注解
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.setBeanNameGenerator(new AnnotationBeanNameGenerator());
        scanner.addIncludeFilter(new AnnotationTypeFilter(ExtReplaceBean.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyBeanDefinitionHandler.class));

        // 如果开启了mock功能，有一些线上别的系统提供的接口，我们在线下或者dev环境就可以模拟返回值了
        if(DubboMockConditional.isEnabled()){
           scanner.addIncludeFilter(new AnnotationTypeFilter(DubboMock.class));
        }

        // 扫描基础包
        scanner.scan("com.example.zhb.study.demo.day9");
        // 缓存 扫描包的路径下的 类和注解的关系，以及类和类的关系
        CustomBeanDefinitionCache cache = new CustomBeanDefinitionCache(registry);
        // 获取Bean定义处理器

        // 获取 添加了 MyBeanDefinitionHandler 注解的所有 被spring管理的bean
        Collection<BeanDefinition> beanDefinitions = cache.getBeanDefinitionsByAnnotation(MyBeanDefinitionHandler.class);

        if(CollectionUtils.isEmpty(beanDefinitions)){
            return;
        }

        List<Class> handlerClasses = beanDefinitions.stream().map(beanDefinition -> {
            if(StringUtils.isBlank(beanDefinition.getBeanClassName())){
                return null;
            }
            Class<?> aClass;
            try {
                aClass = Class.forName(beanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                return null;
            }

            // 如果加了 @MyBeanDefinitionHandler 注解的类 不是 CustomBeanDefinitionHandler 的子类，不做处理
            if(!CustomBeanDefinitionHandler.class.isAssignableFrom(aClass)){
                return null;
            }
            return aClass;
        }).filter(Objects::nonNull).sorted((o1,o2)->{
            // 比较 类的 加载顺序，并且按照 order 排好序
            MyBeanDefinitionHandler annotation1 = o1.getAnnotation(MyBeanDefinitionHandler.class);
            MyBeanDefinitionHandler annotation2 = o2.getAnnotation(MyBeanDefinitionHandler.class);
            return annotation1.order()-annotation2.order();
        }).collect(Collectors.toList());

        // 按照 order 排好序 的类，依次循环调用实现方法，实现业务
        // 这个其实就是在  给 CustomBeanDefinitionHandler 的子类 按照 order 大小来依次调用业务实现
        // 加了 @MyBeanDefinitionHandler 注解，但是不是 CustomBeanDefinitionHandler 的子类 的忽略
        handlerClasses.forEach(clazz->{
            CustomBeanDefinitionHandler handle;
            try {
                // 通过反射得到实例
                handle = (CustomBeanDefinitionHandler)clazz.getConstructor().newInstance();
            } catch (Exception e) {
                log.error("构建Bean定义处理类异常({})",clazz.getName(),e);
                return;
            }
            // 缓存 扫描包的路径下的 类和注解的关系，以及类和类的关系
            handle.handle(cache);
        });
        log.info("自定义Bean处理完成-->");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

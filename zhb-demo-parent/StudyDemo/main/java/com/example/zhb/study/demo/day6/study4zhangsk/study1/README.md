
重写spring bean的几种方式
https://blog.csdn.net/woshilijiuyi/article/details/100916706 

概述
工作中会经常会需要重写jar包中的一个类，开源的jar包或者是其他项目提供的share包，比较通用的做法是下载源码下来，修改后重新打包。但是很多时候我们只是重写一个类，下载源码打包太过繁琐，而且有可能别人的share没有提供源码下载。

可以通过下面几种方式重写：
1、直接同包路径，同报名，进行替换
这种方式简单粗暴，前提必须是同包路径，同类名。可以直接覆盖掉jar包中的类，spring项目会优先加载自定义的类。
比如重写：DefaultResourceFactory类

2、继承要替换的类，在配置类中进行配置，返回其子类
1）首先创建一个新的bean，继承需要被重写的bean

public class MyHealthMessageQueueLogService extends HealthMessageQueueLogService{
    //需要被重写的方法
}

2）在启动类中排除父类
@ComponentScan(excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, value = HealthMessageQueueLogService.class)})
3）在配置类中创建子类
@Configuration
public class MessageQueueLogConfig {
    @Bean
    public HealthMessageQueueLogService healthMessageQueueLogService(){
        return new MyHealthMessageQueueLogService();
    }
}

3、使用BeanDefinitionRegistryPostProcessor
/**
 * Created by zhangshukang on 2019/9/16.
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static String SPECIAL_OVERRIDE_BEAN = "healthMessageQueueLogService";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        boolean isContainsSpecialBean = ((DefaultListableBeanFactory) registry).containsBean(SPECIAL_OVERRIDE_BEAN);

        if (isContainsSpecialBean) {
            AnnotatedBeanDefinition healthMessageBeanDefinition = (AnnotatedBeanDefinition) registry.getBeanDefinition(SPECIAL_OVERRIDE_BEAN);
            if (healthMessageBeanDefinition != null) {
                AnnotatedGenericBeanDefinition myBeanDefinition = new AnnotatedGenericBeanDefinition(healthMessageBeanDefinition.getMetadata());
                //忽略beanClass originatingBeanDefinition字段
                BeanUtils.copyProperties(healthMessageBeanDefinition, myBeanDefinition,"beanClass","originatingBeanDefinition");
                //设置自定义的bean class
                myBeanDefinition.setBeanClass(MyHealthMessageQueueLogService.class);
                //重新加载自定义的bean class
                try {
                    myBeanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                    registry.registerBeanDefinition(SPECIAL_OVERRIDE_BEAN,myBeanDefinition);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
————————————————
版权声明：本文为CSDN博主「张书康」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/woshilijiuyi/article/details/100916706


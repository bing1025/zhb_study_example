package com.example.zhb.study.demo.day9.enhance.initializingBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * ServiceOne 的替换类
 * 在一些以接口注入进去调用的时候，例如
 * @Autowired
 * private ServiceOne serviceOne;
 * 如果加入了 @Primary 并且继承重写那个类，那么 serviceOne接口的实现类就优先走  @Primary  修饰的实现类的业务逻辑
 *
 * 但是注意：这种 @Primary 的写法遇到 spring 按照下面接口处理的业务时候
 * 记得要用本例子中的 @ExtReplaceBean 排除掉类，不然会出现业务重复的问题
 *
 *  向map中注入所有类型为 ServiceInterface 的bean,其中键为bean的名称，value为bean的实例
 *  @Resource
 *  Map<String, ServiceInterface>  serviceInterfaceResourceMap;
 *
 *  或者  向list中注入所有类型为 ServiceInterface 的bean
 *  @Autowired
 *  List<ServiceInterface>  serviceInterfaceList;
 *
 *
 *
 * @Author: zhouhb
 * @date: 2021/11/06/13:43
 * @Description:
 */
@Primary   // 加上 @Primary 表明如果是 调用的 serviceOne 这个接口，优先是以这个类来加载实现的
@Service
@Slf4j
public class ServiceOneExt extends ServiceOne{

    @Override
    public String version() {
        return "one-version ext  ";
    }

    @Override
    public void test() {
        log.info("这是走的one-version ext  ");
    }
}

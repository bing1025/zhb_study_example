
基于Spring Boot + Dubbo的全链路日志追踪(二)
https://segmentfault.com/a/1190000020029090

Dubbo 分布式 日志 追踪
使用dubbo分布式框架进行微服务的开发，一个大系统往往会被拆分成很多不同的子系统，并且子系统还会部署多台机器，当其中一个系统出问题了，查看日志十分麻烦。
所以需要一个固定的流程ID和机器ip地址等来把所有的日志进行染色处理，当然可以通过调用其他接口时参数进行传递，但是这样子对代码的耦合性太强，对代码有侵入性。
我们可以通过dubbo的filter 结合slf4j的MDC或者log4j2的ThreadContext的进行参数的注入，可以直接在日志文件中配置被注入的参数，这样就对系统和日志id打印进行了解耦。
其中当用logback日志的时候是需要调用MDC的方法，而log4j2则需要调用ThreadContext的方法。
https://www.cnblogs.com/phpdragon/p/10520621.html

Spring boot中使用log4j
我们知道，Spring Boot中默认日志工具为logback，但是对于习惯了log4j的开发者，Spring Boot依然可以很好的支持，只是需要做一些小小的配置功能。
Spring Boot使用log4j只需要一下几步
https://www.cnblogs.com/senlinyang/p/8675318.html

我们知道，Spring Boot中默认日志工具为logback，但是对于习惯了log4j的开发者，Spring Boot依然可以很好的支持，只是需要做一些小小的配置功能。Spring Boot使用log4j只需要一下几步

引入log4j依赖
　　在创建Spring Boot工程时，我们引入了spring-boot-starter，其中包含了spring-boot-starter-logging，该依赖内容就是Spring Boot默认的日志框架Logback，所以我们在引入log4j之前，需要先排除该包的依赖，再引入log4j的依赖，就像下面这样

复制代码
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j</artifactId>
</dependency>
复制代码
配置log4j.properties
　　在引入了log4j依赖之后，只需要在src/main/resources目录下加入log4j.properties配置文件，就可以开始对应用的日志进行配置使用。

控制台输出
　　通过如下配置，设定root日志的输出级别为INFO，appender为控制台输出stdout

复制代码
# LOG4J配置
log4j.rootCategory=INFO, stdout

# 控制台输出
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
复制代码
输出到文件
　　在开发环境，我们只是输出到控制台没有问题，但是到了生产或测试环境，或许持久化日志内容，方便追溯问题原因。可以通过添加如下的appender内容，按天输出到不同的文件中去，同时还需要为log4j.rootCategory添加名为file的appender，这样root日志就可以输出到logs/all.log文件中了。

复制代码
log4j.rootCategory=INFO, stdout,file

log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.file=logs/all.log
log4j.appender.file.DatePattern='.'yyyy-MM-dd
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n


spring-boot dubbo 集成 dubbo微服务间的日志id透传，整合多服务日志信息

ProviderApplication 项目中 

1：加一个 ProviderRpcTraceFilter 过滤器，收集

package com.example.zhb.study.demo.common.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.example.zhb.study.demo.common.constants.FacadeConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 日志染色
 *
 */
@Activate(group = {Constants.PROVIDER},order = 1)
public class ProviderRpcTraceFilter implements Filter {
 
    /**
     *
     * @param invoker
     * @param invocation
     * @return
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment("trace_id");
        if (StringUtils.isBlank(traceId)) {
            traceId = this.getUUID() ;
        }
        RpcContext.getContext().setAttachment("trace_id", traceId);
        //设置日志traceId变量
        MDC.put(FacadeConstants.TRACE_ID, traceId);
        try{
            return invoker.invoke(invocation);
        }finally {
            MDC.remove(FacadeConstants.TRACE_ID);
        }
    }
 
    /**
     * 获取UUID
     * @return String UUID
     */
    public String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //替换-字符
        return uuid.replaceAll("-", "");
    }
 
}

2：运用dubbo 的spi 机制 配置 
需要在resources/META-INF/dubbo/文件夹下，
创建com.alibaba.dubbo.rpc.Filter文本文件。
内容为
providerRpcTraceFilter=com.example.zhb.study.demo.common.filter.ProviderRpcTraceFilter

pom文件的引入：

 <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.7.4.1</version>
            <scope>compile</scope>
        </dependency>

        <!--引入 spring boot 和 dubbo 后会出现错误
        java.lang.NoClassDefFoundError: com/alibaba/spring/util/PropertySourcesUtils
        通过引入下面依赖解决-->
        <dependency>
            <groupId>com.alibaba.spring</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>1.0.2</version>
        </dependency>

log4j.properties 中 添加 跟踪日志  [%X{traceId}]
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p [zhb] [%X{traceId}] %c{1}:%L - %m%n
       
        
        
ConsumerApplication 项目中 

1：加一个 ConsumerRpcTraceFilter 过滤器，收集

package com.example.zhb.study.demo.common.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.example.zhb.study.demo.common.constants.FacadeConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 日志染色ConsumerRpcTraceFilter
 * @author phpdragon
 */
@Activate(group = {Constants.CONSUMER})
public class ConsumerRpcTraceFilter implements Filter {

    /**
     * 
     * @param invoker
     * @param invocation
     * @return
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(FacadeConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = this.getUUID() ;
        }

        RpcContext.getContext().setAttachment("trace_id", traceId);
        //设置日志traceId变量
        MDC.put(FacadeConstants.TRACE_ID, traceId);
        return invoker.invoke(invocation);
    }

    /**
     * 获取UUID
     * @return String UUID
     */
    public String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //替换-字符
        return uuid.replaceAll("-", "");
    }

}

2：运用dubbo 的spi 机制 配置 
需要在resources/META-INF/dubbo/文件夹下，
创建com.alibaba.dubbo.rpc.Filter文本文件。
内容为
consumerRpcTraceFilter=com.example.zhb.study.demo.common.filter.ConsumerRpcTraceFilter

pom文件的引入：

  <!--引入dubbo环境-->
         <dependency>
             <groupId>com.alibaba.boot</groupId>
             <artifactId>dubbo-spring-boot-starter</artifactId>
             <version>0.2.0</version>
             <exclusions>
                 <exclusion>
                     <artifactId>dubbo</artifactId>
                     <groupId>com.alibaba</groupId>
                 </exclusion>
             </exclusions>
         </dependency>

         <!--引入 spring boot 和 dubbo 后会出现错误
         java.lang.NoClassDefFoundError: com/alibaba/spring/util/PropertySourcesUtils
         通过引入下面依赖解决-->
         <dependency>
             <groupId>com.alibaba.spring</groupId>
             <artifactId>spring-context-support</artifactId>
             <version>1.0.2</version>
         </dependency>
 
         <dependency>
             <groupId>io.netty</groupId>
             <artifactId>netty-all</artifactId>
             <version>4.1.32.Final</version>
         </dependency>

log4j.properties 中 添加 跟踪日志  [%X{traceId}]
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p [zhb] [%X{traceId}] %c{1}:%L - %m%n



启动 ProviderApplication
启动 ConsumerApplication
发起请求 http://localhost:8063/add?a=99
观察日志：
ProviderApplication中有：
2022-01-12 15:17:35,731  INFO [zhb] [f9c8f1f443f2447f905d92ee3fc7f12c] CostServiceImpl:28 - 生产者带着traceId 返回了

ConsumerApplication中有：
2022-01-12 15:17:35,735  INFO [zhb] [f9c8f1f443f2447f905d92ee3fc7f12c] ProductController:36 - 消费者带着traceId 返回了
2022-01-12 15:17:35,756  INFO [zhb] [f9c8f1f443f2447f905d92ee3fc7f12c] ResponseModifyAdvice:34 - {"code":200,"data":"该产品总共消费 ：1099","msg":"ok"}

最基本的 日志跟踪 完成了!!!!!


考虑到不能每个模块都想要接入链路跟踪功能，
都写 XXXRpcTraceFilter implements Filter
接下来，打算升级，用依赖jar包的方式来实现，只要引入了jar 包，然后在 log4j.properties 配置一下，就都能用

这个在 dubbo-traceId-spi-extension modle中实现

参考这个项目（不不不，其实是完全照抄）
springboot dubbo日志透传
https://github.com/CentMeng/dubbo-spi-extension

代码移植好后，本地 maven install 一下 dubbo-traceId-spi-extension

然后 放到 公共模块里面去，一般是 parent 或者 common 这样的大家都依赖的模块里面的，这里就直接加入到
FacadeDemo 模块里面，因为他是 ConsumerDemo 和 ProviderDemo 的公共模块了

<dependency>
            <groupId>org.example.zhb</groupId>
            <artifactId>dubbo-traceId-spi-extension</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>

关于使用：

项目调用

如果要使用本类，在引用的项目中需要做什么

提供者和消费者配置filter,此处以properties配置文件为例
提供者
dubbo.provider.filter=providerLog,providerTraceId
消费者
dubbo.consumer.filter=consumerTraceId
修改日志配置文件pattern 以logback为例,增加%X{traceId}
<property name="r_pattern" value="%d{yyyy-MM-dd HH:mm:ss} [%p] [%t] [%logger{5}] [%X{traceId}] %m%n"/> 


注意：这种扩展类 的使用不能跟之前的基础类一起兼容使用，要想 使用 这个jar包依赖的方式
就 按照上面那种改法 改完后，记得 屏蔽掉 
ProviderApplication 和 ConsumerApplication 里面的 
resources/META-INF/dubbo/文件夹下，的com.alibaba.dubbo.rpc.Filter

要想使用简单的基础版本 就 屏蔽掉  FacadeDemo 模块
<dependency>
            <groupId>org.example.zhb</groupId>
            <artifactId>dubbo-traceId-spi-extension</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        以及 屏蔽掉 
        提供者
        dubbo.provider.filter=providerLog,providerTraceId
        消费者
        dubbo.consumer.filter=consumerTraceId
        
   ===== jar 包 各种冲突 ，导致试验失败，后面再测试这种 ？？？
   
   找不出来原因，在另外的项目中一试就是可以的 
   具体的可以看 这个项目中：
   D:\zhb\workspace\git-hub\zhb_study_example\spring-boot-dubbo
   
   以后再找干净的 dubbo 生产端，消费端 引入 
   
   <!--自定义日志切面-->
           <dependency>
               <groupId>org.example.zhb</groupId>
               <artifactId>dubbo-traceId-spi-extension</artifactId>
               <version>1.0-SNAPSHOT</version>
               <scope>compile</scope>
           </dependency> 
           来看看情况了
           
   












ApplicationListener 的项目实战应用：

Spring中ApplicationListener的使用
https://www.cnblogs.com/lwcode6/p/12072202.html

ApplicationContext事件机制是观察者设计模式的实现，通过ApplicationEvent类和ApplicationListener接口，可以实现ApplicationContext事件处理；
如果容器中存在ApplicationListener的Bean，当ApplicationContext调用publishEvent方法时，对应的Bean会被触发。
 spring内置事件
内置事件	描述
ContextRefreshedEvent	ApplicationContext 被初始化或刷新时，该事件被触发。这也可以在 ConfigurableApplicationContext接口中使用 refresh() 方法来发生。此处的初始化是指：所有的Bean被成功装载，后处理Bean被检测并激活，所有Singleton Bean 被预实例化，ApplicationContext容器已就绪可用
ContextStartedEvent	当使用 ConfigurableApplicationContext （ApplicationContext子接口）接口中的 start() 方法启动 ApplicationContext 时，该事件被发布。你可以调查你的数据库，或者你可以在接受到这个事件后重启任何停止的应用程序。
ContextStoppedEvent	当使用 ConfigurableApplicationContext 接口中的 stop() 停止 ApplicationContext 时，发布这个事件。你可以在接受到这个事件后做必要的清理的工作。
ContextClosedEvent	当使用 ConfigurableApplicationContext 接口中的 close() 方法关闭 ApplicationContext 时，该事件被发布。一个已关闭的上下文到达生命周期末端；它不能被刷新或重启。
RequestHandledEvent	这是一个 web-specific 事件，告诉所有 bean HTTP 请求已经被服务。只能应用于使用DispatcherServlet的Web应用。在使用Spring作为前端的MVC控制器时，当Spring处理用户请求结束后，系统会自动触发该事件。
 同样事件可以自定义、监听也可以自定义，完全根据自己的业务逻辑来处理。
 
 com.example.zhb.study.demo.message 这个 message 包下面就是项目实战时候截取的一部分应用
 
 本示例演示 自定义监听器以及自定义事件，按照自己逻辑灵活处理业务
 
 自定义事件及监听，以发送邮件为例
 自定义邮件通知事件类：EmailEvent
 package com.lw.coodytest.event;
 
 import org.springframework.context.ApplicationEvent;
 
 /**
  * @Classname EmailEvent
  * @Description 邮件通知事件
  * @Author lw
  * @Date 2019-12-20 11:05
  */
 public class EmailEvent extends ApplicationEvent {
 
     private String email;
 
     private String content;
 
     public EmailEvent(Object source){
         super(source);
     }
 
     public EmailEvent(Object source, String email, String content){
         super(source);
         this.email = email;
         this.content = content;
     }
 
     public String getEmail() {
         return email;
     }
 
     public void setEmail(String email) {
         this.email = email;
     }
 
     public String getContent() {
         return content;
     }
 
     public void setContent(String content) {
         this.content = content;
     }
 }

 自定义邮件通知监听类：EmailListener
 package com.lw.coodytest.listener;
 
 import com.lw.coodytest.event.EmailEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.stereotype.Component;
 
 /**
  * @Classname EmailListener
  * @Description 邮件通知监听
  * @Author lw
  * @Date 2019-12-20 11:10
  */
 @Component
 public class EmailListener implements ApplicationListener<EmailEvent> {
     @Override
     public void onApplicationEvent(EmailEvent emailEvent) {
         System.out.println("邮件地址：" + emailEvent.getEmail());
         System.out.println("邮件内容：" + emailEvent.getContent());
     }
 }

 单元测试类
 package com.lw.coodytest.junit;
 
 import com.lw.coodytest.event.EmailEvent;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.test.context.junit4.SpringRunner;
 import org.springframework.web.context.WebApplicationContext;
 
 /**
  * @Classname ListenerTest
  * @Description 监听测试类
  * @Author lw
  * @Date 2019-12-20 11:12
  */
 @RunWith(SpringRunner.class)
 @SpringBootTest
 public class ListenerTest {
     @Autowired
     private WebApplicationContext webapplicationcontext;
     @Test
     public void testListener(){
         EmailEvent emailEvent = new EmailEvent("object", "172572575@qq.com", "###listener");
         webapplicationcontext.publishEvent(emailEvent);
     }
 }
 复制代码
 监听器通过@Component注解进行实例化，并在onApplicationEvent中打印相关信息
 执行测试类，可以看到
  至此，便完成了一个自定义事件及监听类的实现和实例化。
  特别注意：
 　　不管是内置监听还是外部自定义监听一定要把实现ApplicationListener的类定义成一个bean才行，可以通过注解@Component或者在bean.xml中定义来实现。
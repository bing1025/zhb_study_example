spring 的一些扩展使用
1：本地接口mock测试 
  新建一个类并实现dubbo消费接口后，在该类上面加上 com.example.zhb.study.demo.day9.enhance.anno.DubboMock 注解即可
  @DubboMock生效是有 com.example.zhb.study.demo.day9.enhance.component.DubboMockConditional 控制的，具体的控制逻辑可以在里面去编排
  由于 本项目 没有引入dubbo来分消费端，服务端的方式调用，因此没有合适例子提供
  
2：替换任意交给spring管理的类
   新建一个替换类，在该类上面加上 com.example.zhb.study.demo.day9.enhance.anno.ExtReplaceBean 注解
   @ExtReplaceBean 注解内的 classTypes属性 代表的是需要被替换的类，可以配置多个，
   deleteClasses属性 代表的 是需要被移除的类，例如想要移除项目中的某个类，而不是替换就可以用这个属性

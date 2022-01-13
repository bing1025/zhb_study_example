springboot项目启动自动执行代码几种方案
https://blog.csdn.net/feinifi/article/details/108799504?spm=1001.2101.3001.6650.14&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-14.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-14.no_search_link

在spring或者springboot项目中，经常会有这样的需求，就是项目启动之后，会执行一段代码，做一些初始化的操作，执行完毕，就不再重复执行。

    在spring项目中，可以通过实现InitializingBean接口的方式来实现，
    另外，项目如果是springweb项目，那么可以通过实现ServletContextListener和ServletContextAware接口等来实现。

    在springboot项目中，可以通过实现ApplicationRunner接口和CommandLineRunner接口。

    还有一种方式，不用实现接口，通过注解@PostConstruct来实现。

    下面给出以上列举的6中方式实现代码：
————————————————
版权声明：本文为CSDN博主「luffy5459」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/feinifi/article/details/108799504
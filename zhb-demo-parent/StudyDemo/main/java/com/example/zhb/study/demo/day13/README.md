SkyWalking 分布式追踪系统
https://skywalking.apache.org/

帮助理解系统行为、用于分析性能问题的工具，以便发生故障的时候，能够快速定位和解决问题，这时候 APM（应用性能管理）工具就该闪亮登场了。
目前主要的一些 APM 工具有: Cat、Zipkin、Pinpoint、SkyWalking，这里主要介绍 SkyWalking ，
它是一款优秀的国产 APM 工具，包括了分布式追踪、性能指标分析、应用和服务依赖分析等。
作者：BeckJin
链接：https://www.jianshu.com/p/2fd56627a3cf
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

未完待续。。。等以后自己搭建起来玩耍一下

在windows上搭建SkyWalking
https://blog.csdn.net/Startapi/article/details/106531288

SkyWalking学习笔记(Window环境 本地环境)
https://blog.csdn.net/sD7O95O/article/details/104322792

Elasticsearch学习第1篇——下载、启动（windows版本6.8.1）
https://blog.csdn.net/m0_37617778/article/details/100092044

用管理员执行cmd命令
启动 elasticsearch
D:\software\elasticsearch-6.8.8\bin>elasticsearch-service.bat start
The service 'elasticsearch-service-x64' has been started

关闭 elasticsearch
D:\software\elasticsearch-6.8.8\bin>elasticsearch-service.bat stop
The service 'elasticsearch-service-x64' has been stopped


整体主要分为三个部分:

1.skywalking-collector：链路数据归集器，数据可以保存在H2或ElasticSearch

2.skywalking-web：web的可视化管理后台，可以查看归集的数据

3.skywalking-agent：探针，用来收集和推送数据到归集器


然后修改一下Skywalking的启动端口，默认是8080，但我的端口被占用了。
进入webapp目录，编辑webapp.yml文件 更改成 9090

接下来启动 Skywalking，进入bin目录
 这里webappService.bat 是启动UI界面，oapService.bat是启动后端，所以我们选择startup.bat（因为是他们的集合）


————————————————
版权声明：本文为CSDN博主「吹灯老人」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_44110772/article/details/102988195


SkyWalking8.1.0使用及安装记录 
https://blog.csdn.net/qq_37223629/article/details/108622960





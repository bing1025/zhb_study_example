package com.example.zhb.study.demo.flinkcdc;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * 问题12
 *   - Cannot read the binlog filename and position via 'SHOW MASTER STATUS'
 *   - mysql数据库没开binlog 或者binlog格式不是row
 *   - 参考 https://www.jianshu.com/p/0a47e387de51
 *   - 查找my.cnf位置
 *     - 参考 https://stackoverflow.com/questions/38490785/where-is-mysql-5-7-my-cnf-file
 *     - mysqld --help --verbose|grep cnf
 *     - 有三个位置 挨个查一下 发现用的是第二个 即 /etc/mysql/my.cnf
 *   - 查看my.cnf中是否已有binlog相关配置 作者：mayoiwill https://www.bilibili.com/read/cv14717218/ 出处：bilibili
 *
 * MySQL 启用binlog
 * 接下来以MySQL CDC为例，和大家一起配置Flink MySQL CDC。
 * 在使用CDC之前务必要开启MySQl的binlog。下面以MySQL 5.7版本为例说明。
 * 修改my.cnf文件，增加：
 * server_id=1
 * log_bin=mysql-bin
 * binlog_format=ROW
 * expire_logs_days=30
 * binlog_do_db=db_a
 * binlog_do_db=db_b
 * 配置项的解释如下：
 *
 * server_id：MySQL5.7及以上版本开启binlog必须要配置这个选项。对于MySQL集群，不同节点的server_id必须不同。对于单实例部署则没有要求。
 * log_bin：指定binlog文件名和储存位置。如果不指定路径，默认位置为/var/lib/mysql/。
 * binlog_format：binlog格式。有3个值可以选择：ROW：记录哪条数据被修改和修改之后的数据，会产生大量日志。STATEMENT：记录修改数据的SQL，日志量较小。MIXED：混合使用上述两个模式。CDC要求必须配置为ROW。
 * expire_logs_days：bin_log过期时间，超过该时间的log会自动删除。
 * binlog_do_db：binlog记录哪些数据库。如果需要配置多个库，如例子中配置多项。切勿使用逗号分隔。
 * 配置文件修改完毕后保存并重启MySQL。然后进入MySQL命令行，验证是否已启用binlog：
 * mysql> show variables like '%bin%';
 *
 * 作者：AlienPaul
 * 链接：https://www.jianshu.com/p/0a47e387de51
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * 使用Docker安装mysql8
 * https://blog.csdn.net/kyq_1024yahoocn/article/details/131534188
 *
 */
public class FlinkCdcDwdDeserializationSchemaTest {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        SourceFunction<JSONObject> sourceFunction = MySQLSource.<JSONObject>builder()
                .hostname("192.168.10.132")
                .port(13306)
                .databaseList("test") // monitor all tables under inventory database
 
                .username("root")
                //.password("abc123")
                .password("root")
                .deserializer(new CdcDwdDeserializationSchema()) // converts SourceRecord to String
                .build();
 
 
        DataStreamSource<JSONObject> stringDataStreamSource = env.addSource(sourceFunction);
 
        stringDataStreamSource.print("===>");
 
 
        try {
            env.execute("测试mysql-cdc");
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
}
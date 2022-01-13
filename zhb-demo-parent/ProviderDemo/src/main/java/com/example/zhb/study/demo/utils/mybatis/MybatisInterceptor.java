package com.example.zhb.study.demo.utils.mybatis;

import com.example.zhb.study.demo.bean.SqlLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Properties;

/**
 * Mybatis拦截器
 * https://blog.csdn.net/wuyuxing24/article/details/89343951
 *
 * 自定义一个MybatisInterceptor类，来拦截Executor类里面的query,update方法。
 * 其中：Executor 类里面 新增和修改最后 method 都统一成 update
 * 自定义拦截类MybatisInterceptor
 *
 *
 */
@Slf4j
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        ),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class MybatisInterceptor implements Interceptor {

    @Value("${can.method.be.called}")
    private String methodCalledBool;

   /* 这里要讲的巧妙用法是用来实现在拦截器中执行额外 MyBatis 现有方法的用法。
    并且会提供一个解决拦截Executor时想要修改MappedStatement时解决并发的问题。
    这里假设一个场景：
    实现一个拦截器，记录 MyBatis 所有的 insert,update,delete 操作，将记录的信息存入数据库。
    这个用法在这里就是将记录的信息存入数据库。
    实现过程的关键步骤和代码：
            1.首先在某个 Mapper.xml 中定义好了一个往日志表中插入记录的方法，假设方法为id="insertSqlLog"。
            2.日志表相关的实体类为SqlLog.*/

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //自定义拦截逻辑
        if("Y".equals(methodCalledBool)){
            // 一个mappedStatement对象对应Mapper配置文件中的一个select/update/insert/delete节点，主要描述的是一条sql语句。
            //获取参数1：MappedStatement对象
            // MappedStatement对象详解 https://www.cnblogs.com/mimimikasa/p/15531142.html
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            SqlLog log = new SqlLog();
            Configuration configuration = ms.getConfiguration();
            Object target = invocation.getTarget();
            StatementHandler handler = configuration.newStatementHandler((Executor) target, ms,
                    parameter, RowBounds.DEFAULT, null, null);
            BoundSql boundSql = handler.getBoundSql();
            //记录SQL
            log.setSqlClause(boundSql.getSql());
            //执行真正的 进来时候的 sql 方法
            Object result = invocation.proceed();
            //记录影响行数
            // 查询方法 不用记录条数 后期补上
            // log.setResult(Integer.valueOf(Integer.parseInt(result.toString())));
            log.setResult(999);
            //记录时间
            log.setWhenCreated(new Date());
            //TODO 还可以记录参数，或者单表id操作时，记录数据操作前的状态
            //获取insertSqlLog方法
            ms = ms.getConfiguration().getMappedStatement("insertSqlLog");
            //替换当前的参数为新的ms
            args[0] = ms;
            //insertSqlLog 方法的参数为 log
            args[1] = log;
            //执行insertSqlLog方法
            // invocation.proceed() 这个是调用 往日志表中插入记录的方法，假设方法为id="insertSqlLog"
            // /* 注：此处实际上使用Invocation.proceed()方法完成interceptorChain链的遍历调用(即执行所有注册的Interceptor的intercept方法)，到最终被代理对象的原始方法调用 */
            invocation.proceed();
            //返回真正方法执行的结果
            return result;

        }
        // 不拦截的话就直接调用sql本身业务逻辑
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this); // 返回代理类
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

// org.apache.ibatis.plugin.Interceptor 接口注释
/*public interface Interceptor {

    *//**
     * 代理对象每次调用的方法，就是要进行拦截的时候要执行的方法。在这个方法里面做我们自定义的逻辑处理
     *//*
    Object intercept(Invocation invocation) throws Throwable;

    *//**
     * plugin方法是拦截器用于封装目标对象的，通过该方法我们可以返回目标对象本身，也可以返回一个它的代理
     *
     * 当返回的是代理的时候我们可以对其中的方法进行拦截来调用intercept方法 -- Plugin.wrap(target, this)
     * 当返回的是当前对象的时候 就不会调用intercept方法，相当于当前拦截器无效
     *//*
    Object plugin(Object target);

    *//**
     * 用于在Mybatis配置文件中指定一些属性的，注册当前拦截器的时候可以设置一些属性
     *//*
    void setProperties(Properties properties);

}*/



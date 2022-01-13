/**
 * @(#)DubboTraceIdFilter.java, 7月 31, 2020.
 * <p>
 * Copyright 2020 bb.bj.cn. All rights reserved.
 * bb.bj.cn PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.zhb.study.demo.filter;


import com.example.zhb.study.demo.util.ThreadMdcUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 消费者traceId拦截器
 * @author Vincent.M mengshaojie@188.com on 2020/7/31.
 */
@Activate(group = {CommonConstants.CONSUMER},order = 0)
public class ConsumerTraceIdFilter implements Filter {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ConsumerTraceIdFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = ThreadMdcUtil.getTraceId();
        if (StringUtils.isEmpty(traceId)) {
            // *) 从RpcContext里获取traceId并保存
            traceId = UUID.randomUUID().toString();
            LOG.info("[dubbo] [filter] [ConsumerTraceIdFilter],new traceId:{}",traceId);
        }else{
            LOG.info("[dubbo] [filter] [ConsumerTraceIdFilter],traceId:{}",traceId);
        }
        RpcContext.getContext().setAttachment(ThreadMdcUtil.LOG_TRACE_ID, traceId);
        //设置日志traceId变量
        MDC.put("traceId", traceId);
        // *) 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
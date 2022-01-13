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
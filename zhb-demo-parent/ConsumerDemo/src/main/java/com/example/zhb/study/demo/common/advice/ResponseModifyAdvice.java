package com.example.zhb.study.demo.common.advice;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.common.constants.FacadeConstants;
import com.example.zhb.study.demo.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author lpe234
 * @since 2019/5/25 15:03
 */
@ControllerAdvice
@Slf4j
public class ResponseModifyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info(JSONObject.toJSONString(object));
        String traceId = RpcContext.getContext().getAttachment("trace_id");

        if (object instanceof RestResponse) {
            // put traceId to response
            ((RestResponse) object).setTraceId(MDC.get(FacadeConstants.TRACE_ID));
        }
        return object;
    }

    @ResponseBody
    @ExceptionHandler
    String exceptionHandler(Exception exception) {
        //统一处理异常,返回规定的json格式
        return JSONObject.toJSONString(RestResponse.error(exception.getMessage()));
    }
}
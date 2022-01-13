package com.example.zhb.study.demo.common.interceptor;


import com.example.zhb.study.demo.common.constants.ConsumerConstants;
import com.example.zhb.study.demo.common.constants.FacadeConstants;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author lpe234
 * @since 2019/5/25 14:43
 */
public class TraceIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // generate traceId
        String traceId = UUID.randomUUID().toString().replace("-", "");

        // put traceId
        MDC.put(FacadeConstants.TRACE_ID, traceId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // clear traceId
        MDC.remove(FacadeConstants.TRACE_ID);
    }
}
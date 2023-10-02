package com.example.zhb.study.demo.common.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//@Component
//@PropertySource("classpath:application-dev.yml")
//@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
//public class CorsFilter implements Filter {
//
//    @Value("${bpm.fiter.domain}")
//    private String allowDomains;
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest reqs = (HttpServletRequest) req;
//
//        // 设置允许多个域名请求
//        //String[] allowDomains = {"http://www.xxxx.xin","http://xxxx:8080","http://localhost:8080"};
//        String[] allowDomain = allowDomains.split(",");
//        Set allowOrigins = new HashSet(Arrays.asList(allowDomain));
//        String curOrigin = reqs.getHeader("Origin");
//        /*if("null".equalsIgnoreCase(curOrigin)){
//            curOrigin = "http://xxxxxx:8888";
//        }*/
//        if(allowOrigins.contains(curOrigin) || null == curOrigin){
//            //设置允许跨域的配置
//            // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
//            response.setHeader("Access-Control-Allow-Origin", curOrigin);
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            //response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
//
//            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
//
//            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
//            chain.doFilter(reqs, response);
//
//        }else{
//            throw new IOException(ResultUtils.doFilter().toString());
//        }
//
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {}
//
//    @Override
//    public void destroy() {}
//}
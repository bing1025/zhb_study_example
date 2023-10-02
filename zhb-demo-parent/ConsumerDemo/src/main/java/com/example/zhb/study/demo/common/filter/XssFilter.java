package com.example.zhb.study.demo.common.filter;
import com.example.zhb.study.demo.common.wapper.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * springboot 项目前后端接口，防止xss攻击以及跨域问题解决
 * springboot自定义CORS&XSS拦截器
 * 目录
 * 1、启动类添加注解
 * 2、cors的拦截类
 * 3、xss相关类
 * 4、yml中配置
 * 5、返回
 * 6、自定义code
 *
 * springboot 项目前后端接口，防止xss攻击以及跨域问题解决
 * https://www.cnblogs.com/fmgao-technology/p/11163989.html
 */

/** * XSS过滤器 * @author Jozz */
@WebFilter(filterName="xssFilter",urlPatterns="/*")
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                                   ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest; String path = request.getServletPath();
        //由于我的@WebFilter注解配置的是urlPatterns="/*"(过滤所有请求),所以这里对不需要过滤的静态资源url,作忽略处理(大家可以依照具体需求配置)
        String[] exclusionsUrls = {".js",".gif",".jpg",".png",".css",".ico"};
        for (String str : exclusionsUrls) {
            if (path.contains(str)) {
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        filterChain.doFilter(new XssHttpServletRequestWrapper(request),servletResponse);
    }
    @Override public void destroy() {

    }
}
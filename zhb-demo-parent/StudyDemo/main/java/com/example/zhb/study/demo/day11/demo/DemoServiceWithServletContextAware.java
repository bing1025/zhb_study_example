package com.example.zhb.study.demo.day11.demo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
@Component
public class DemoServiceWithServletContextAware implements ServletContextAware{
 
	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("4: run with ServletContextAware , method -> setServletContext().");
	}
 
}
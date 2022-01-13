package com.example.zhb.study.demo.day11.demo;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
@Component
public class DemoServiceWithServletContextListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("1: run with ServletContextListener , method -> contextInitialized().");
	}
}
package com.example.zhb.study.demo.day11.demo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class DemoServiceWithInitializingBean implements InitializingBean{
 
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("2: run with InitializingBean , method -> afterPropertiesSet().");
	}
	
}
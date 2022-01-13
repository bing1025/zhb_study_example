package com.example.zhb.study.demo.day11.demo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
@Component
public class DemoServiceWithApplicationRunner implements ApplicationRunner{
 
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("5: run with ApplicationRunner , method -> run().");
	} 
 
}
package com.example.zhb.study.demo.day11.demo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class DemoServiceWithPostConstruct {
	@PostConstruct
	public void run() {
		System.out.println("3: run with PostConstruct , method -> run().");
	}
}
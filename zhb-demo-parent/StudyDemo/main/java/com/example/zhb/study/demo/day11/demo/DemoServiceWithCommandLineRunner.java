package com.example.zhb.study.demo.day11.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DemoServiceWithCommandLineRunner implements CommandLineRunner{
 
	@Override
	public void run(String... args) throws Exception {
		System.out.println("6: run with CommandLineRunner , method -> run().");
	}
 
}
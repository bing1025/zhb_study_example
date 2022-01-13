package com.github.wpinggao.springboot.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo-provider.xml")
public class SpringBootDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboProviderApplication.class, args);
    }

}

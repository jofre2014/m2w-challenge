package com.challenge.w2m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.challenge.w2m")
public class W2mApplication {

	public static void main(String[] args) {
		SpringApplication.run(W2mApplication.class, args);
	}

}

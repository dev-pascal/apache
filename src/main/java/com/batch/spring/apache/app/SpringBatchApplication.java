package com.batch.spring.apache.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.batch.spring.apache.config")
public class SpringBatchApplication {

	public static void main(String... args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}

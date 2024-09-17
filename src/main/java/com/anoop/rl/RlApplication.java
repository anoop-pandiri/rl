package com.anoop.rl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.anoop.rl.config.dotenv.DotenvConfig;

@SpringBootApplication
public class RlApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(DotenvConfig.class);
		SpringApplication.run(RlApplication.class, args);
	}

}

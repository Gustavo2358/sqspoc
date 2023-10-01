package com.gustavo.sqspoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SqspocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqspocApplication.class, args);
	}

}

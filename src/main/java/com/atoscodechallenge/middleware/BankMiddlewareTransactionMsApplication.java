package com.atoscodechallenge.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.atoscodechallenge.middleware"})
public class BankMiddlewareTransactionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankMiddlewareTransactionMsApplication.class, args);
	}

}

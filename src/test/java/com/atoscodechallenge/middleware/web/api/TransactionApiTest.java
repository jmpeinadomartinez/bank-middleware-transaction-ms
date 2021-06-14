package com.atoscodechallenge.middleware.web.api;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;


class TransactionApiTest {
	
	@Test
	void testDefaultMethod() {

		TransactionApi transactionApi = new TransactionApi() {};

		assertEquals(transactionApi.newTransaction(new NewTransactionRequest())
				.getStatusCode().toString(),HttpStatus.OK.toString());

		assertEquals(transactionApi.searchTransaction("iban", "sort", 0, 10)
				.getStatusCode().toString(),HttpStatus.OK.toString());

		assertEquals(transactionApi.transactionStatus(new TransactionStatusRequest())
				.getStatusCode().toString(),HttpStatus.OK.toString());
	  }
}

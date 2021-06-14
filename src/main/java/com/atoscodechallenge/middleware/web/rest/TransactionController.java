package com.atoscodechallenge.middleware.web.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.atoscodechallenge.middleware.service.TransactionService;
import com.atoscodechallenge.middleware.web.api.TransactionApi;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionListResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusResponse;


@Controller
public class TransactionController implements TransactionApi {

	@Autowired 
	private TransactionService transactionService;
	
	@Override
	public ResponseEntity<NewTransactionResponse> newTransaction(@Valid @RequestBody NewTransactionRequest newTransactionRequest) {

		return new ResponseEntity<>(transactionService.newTransaction(newTransactionRequest),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TransactionListResponse> searchTransaction(String iban, @NotNull String sort,
			Integer page, Integer size) {

		return new ResponseEntity<>(transactionService.searchTransaction(iban, sort, page, size),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TransactionStatusResponse> transactionStatus(@Valid @RequestBody TransactionStatusRequest transactionStatusRequest) {

		return new ResponseEntity<>(transactionService.transactionStatus(transactionStatusRequest),HttpStatus.OK);
	}

}
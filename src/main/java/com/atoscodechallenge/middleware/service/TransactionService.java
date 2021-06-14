package com.atoscodechallenge.middleware.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;

import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionListResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusResponse;

public interface TransactionService {

	NewTransactionResponse newTransaction(@Valid @RequestBody NewTransactionRequest newTransactionRequest);

	TransactionListResponse searchTransaction(String iban, @NotNull String sort, Integer page,
			Integer size);

	TransactionStatusResponse transactionStatus(@Valid @RequestBody TransactionStatusRequest transactionStatusRequest);

}

package com.atoscodechallenge.middleware.service.impl;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.atoscodechallenge.middleware.domain.MdwTransaction;
import com.atoscodechallenge.middleware.repository.MdwTransactionRepository;
import com.atoscodechallenge.middleware.service.TransactionService;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionResponse;
import com.atoscodechallenge.middleware.web.api.model.Transaction;
import com.atoscodechallenge.middleware.web.api.model.TransactionListResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest.ChannelEnum;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private MdwTransactionRepository mdwTransactionRepository;
	
	private static final String STATUS_PENDING = "PENDING";
	private static final String STATUS_SETTLED = "SETTLED";
	private static final String STATUS_FUTURE = "FUTURE";
	private static final String STATUS_INVALID = "INVALID";
	
	private static final String MSG_PARAMETER_EXISTS = "Reference already exists in database";
	private static final String MSG_INVALID_FORMAT_DATE = "Invalid format date";
	
	
	@Override
	public NewTransactionResponse newTransaction(@Valid @RequestBody NewTransactionRequest newTransactionRequest) {
		
		log.info("newTransaction - init");
		
		String reference = null;
		
		if (newTransactionRequest.getReference()!=null) {

			boolean ok = referenceValidate(newTransactionRequest.getReference()).isPresent();
			
			if (ok) {
				log.error("Campo reference existente en base de datos");
				throw new InvalidParameterException(MSG_PARAMETER_EXISTS);
			}
			
			reference = newTransactionRequest.getReference();
			
		} else {
			
			reference = referenceGenerate();
		}
		
		
		ZonedDateTime date = null;
		if (newTransactionRequest.getDate()!=null) {
			try {
				date = ZonedDateTime.parse(newTransactionRequest.getDate());
			} catch (DateTimeParseException e) {
				log.error("Campo date inválido");
				throw new InvalidParameterException(MSG_INVALID_FORMAT_DATE);
			}
		} else {
			date = ZonedDateTime.now();
		}

		// Mejorar parseo
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setReference(reference);
		mdwTransaction.setAccountIban(newTransactionRequest.getAccountIban());
		mdwTransaction.setDate(date);
		mdwTransaction.setAmount(newTransactionRequest.getAmount());
		if(newTransactionRequest.getFee()!=null) {
			mdwTransaction.setFee(newTransactionRequest.getFee());
		} else {
			mdwTransaction.setFee(0.0);
		}
		mdwTransaction.setDescription(newTransactionRequest.getDescription());
		mdwTransactionRepository.save(mdwTransaction);
		
		log.info("newTransaction - end");
		
		NewTransactionResponse newTransactionResponse = new NewTransactionResponse();
		newTransactionResponse.setReference(reference);
		return newTransactionResponse;
	}

	/**
	 * Lógica de generación de reference aleatoria si no viene informado en el objeto de entrada
	 * @return reference
	 */
	private String referenceGenerate() {
		
		String newReference = null;

		boolean ok = false;

		do {
			UUID random = UUID.randomUUID();
			newReference = random.toString().substring(30);

			ok = referenceValidate(newReference).isPresent();
		}while(ok);
		
		return newReference;
	}

	/**
	 * Lógica búsqueda de reference en bbdd
	 * @param reference
	 * @return Optional<MdwTransaction>
	 */
	private Optional<MdwTransaction> referenceValidate(String reference) {
		return mdwTransactionRepository.findByReference(reference);
	}

	@Override
	public TransactionListResponse searchTransaction(String iban, @NotNull String sort, Integer page,
			Integer size) {
		
		log.info("searchTransaction - init");
		
		if(page==null) {
			page=0;
		}
		if(size==null) {
			size=1000;
		}
		
		Pageable sortedByAmount = null;
		
		switch(sort) {
		
			case "descending":
				sortedByAmount = PageRequest.of(page, size, Sort.by("amount").descending());
				break;
				
			case "ascending":
			default:
				sortedByAmount = PageRequest.of(page, size, Sort.by("amount").ascending());
				break;
		}

		List<MdwTransaction> mdwTransactionList = mdwTransactionRepository.findAllByAccountIban(iban, sortedByAmount);

		
		List<Transaction> transactionList = new ArrayList<>();
		
		mdwTransactionList.forEach((MdwTransaction mdwTransaction) -> {

			Transaction transaction = new Transaction();
			transaction.setAccountIban(mdwTransaction.getAccountIban());
			transaction.setAmount(mdwTransaction.getAmount());
			transaction.setDate(mdwTransaction.getDate().toString());
			transaction.setDescription(mdwTransaction.getDescription());
			transaction.setFee(mdwTransaction.getFee());
			transaction.setReference(mdwTransaction.getReference());
			
			transactionList.add(transaction);
		});
		
		
		TransactionListResponse transactionListResponse = new TransactionListResponse();
		transactionListResponse.setTransactions(transactionList);
		transactionListResponse.setTotalTransaction(transactionList.size());
		
		log.info("searchTransaction - end");
		
		return transactionListResponse;
	}

	@Override
	public TransactionStatusResponse transactionStatus(@Valid @RequestBody TransactionStatusRequest transactionStatusRequest) {
		
		log.info("transactionStatus - init");
		
		TransactionStatusResponse transactionStatusResponse = new TransactionStatusResponse();
		
		Optional<MdwTransaction> mdwTransactionOpt = referenceValidate(transactionStatusRequest.getReference());
		
		// Businnew Rules A
		if (mdwTransactionOpt.isEmpty()) {
			log.info("Businnew Rules A");
			transactionStatusResponse.setReference(transactionStatusRequest.getReference());
			transactionStatusResponse.setStatus(STATUS_INVALID);
			return transactionStatusResponse;
		}
		
		
		// Dia de hoy
		LocalDate localDate = LocalDate.now();
		
		// Dia de la transacción 
		ZonedDateTime zonedDateTimeTransaction = mdwTransactionOpt.get().getDate();
		LocalDate localDateTransaction = zonedDateTimeTransaction.toLocalDate();
		
		/**
		 * Compare today & transaction date
		 * if result < 0 -> transaction date is before today
		 * if result = 0 -> transaction date is equals to today
		 * if result > 0 -> transaction date is greater than today
		 */
		int result = localDateTransaction.compareTo(localDate); 
		
		
		if( (transactionStatusRequest.getChannel().equals(ChannelEnum.CLIENT)) || 
				(transactionStatusRequest.getChannel().equals(ChannelEnum.ATM)) ){
			
			if(result<0) {
				// Businnew Rules B
				log.info("Businnew Rules B");
				transactionStatusResponse.setReference(transactionStatusRequest.getReference());
				transactionStatusResponse.setStatus(STATUS_SETTLED);
				transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount() - mdwTransactionOpt.get().getFee());
				return transactionStatusResponse;
			} else if (result==0) {
				// Businnew Rules D
				log.info("Businnew Rules D");
				transactionStatusResponse.setReference(transactionStatusRequest.getReference());
				transactionStatusResponse.setStatus(STATUS_PENDING);
				transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount() - mdwTransactionOpt.get().getFee());
				return transactionStatusResponse;
			}
		}
		
		// Businnew Rules F
		if( transactionStatusRequest.getChannel().equals(ChannelEnum.CLIENT) &&
				(result>0) ){
			log.info("Businnew Rules F");
			transactionStatusResponse.setReference(transactionStatusRequest.getReference());
			transactionStatusResponse.setStatus(STATUS_FUTURE);
			transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount() - mdwTransactionOpt.get().getFee());
			return transactionStatusResponse;
		}
		
		// Businnew Rules G
		if( (transactionStatusRequest.getChannel().equals(ChannelEnum.ATM)) &&
				(result>0) ){
			log.info("Businnew Rules G");
			transactionStatusResponse.setReference(transactionStatusRequest.getReference());
			transactionStatusResponse.setStatus(STATUS_PENDING);
			transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount() - mdwTransactionOpt.get().getFee());
			return transactionStatusResponse;
		}		

		if(transactionStatusRequest.getChannel().equals(ChannelEnum.INTERNAL)){
			
			if(result<0) {
				// Businnew Rules C
				log.info("Businnew Rules C");
				transactionStatusResponse.setReference(transactionStatusRequest.getReference());
				transactionStatusResponse.setStatus(STATUS_SETTLED);
				transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount());
				transactionStatusResponse.setFee(mdwTransactionOpt.get().getFee());
				return transactionStatusResponse;
			} else if (result==0) {
				// Businnew Rules E
				log.info("Businnew Rules E");
				transactionStatusResponse.setReference(transactionStatusRequest.getReference());
				transactionStatusResponse.setStatus(STATUS_PENDING);
				transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount());
				transactionStatusResponse.setFee(mdwTransactionOpt.get().getFee());
				return transactionStatusResponse;
				
			} else {
				// Businnew Rules H
				log.info("Businnew Rules H");
				transactionStatusResponse.setReference(transactionStatusRequest.getReference());
				transactionStatusResponse.setStatus(STATUS_FUTURE);
				transactionStatusResponse.setAmount(mdwTransactionOpt.get().getAmount());
				transactionStatusResponse.setFee(mdwTransactionOpt.get().getFee());
				return transactionStatusResponse;
			}
		}
		
		log.info("transactionStatus - end");
		
		return transactionStatusResponse;
	}

}

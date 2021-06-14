package com.atoscodechallenge.middleware.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.atoscodechallenge.middleware.domain.MdwTransaction;
import com.atoscodechallenge.middleware.repository.MdwTransactionRepository;
import com.atoscodechallenge.middleware.service.impl.TransactionServiceImpl;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionListResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest.ChannelEnum;


class TransactionServiceImplTest {

	private static final String STATUS_PENDING = "PENDING";
	private static final String STATUS_SETTLED = "SETTLED";
	private static final String STATUS_FUTURE = "FUTURE";
	private static final String STATUS_INVALID = "INVALID";
	
	
    @InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

    
    @Mock
    private MdwTransactionRepository mdwTransactionRepository;

    @BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	void newTransaction_Test() throws Exception {

		Optional<MdwTransaction> value = Optional.empty();
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.any(String.class))).thenReturn(value );

		NewTransactionRequest newTransactionRequest = new NewTransactionRequest();
		newTransactionRequest.setReference("12345A");
		newTransactionRequest.setAccountIban("ES9820385778983000760236");
		newTransactionRequest.setDate("2019-07-16T16:55:42.000Z");
		newTransactionRequest.setAmount(193.38);
		newTransactionRequest.setFee(3.18);
		newTransactionRequest.setDescription("Restaurant payment");

		NewTransactionResponse resp = transactionServiceImpl.newTransaction(newTransactionRequest );
		assertNotNull(resp);
	}
	
	@Test
	void newTransaction_WithoutReference_Test() throws Exception {

		Optional<MdwTransaction> value = Optional.empty();
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		NewTransactionRequest newTransactionRequest = new NewTransactionRequest();
		newTransactionRequest.setAccountIban("ES9820385778983000760236");
		newTransactionRequest.setDate("2019-07-16T16:55:42.000Z");
		newTransactionRequest.setAmount(193.38);
		newTransactionRequest.setFee(3.18);
		newTransactionRequest.setDescription("Restaurant payment");

		NewTransactionResponse resp = transactionServiceImpl.newTransaction(newTransactionRequest );
		assertNotNull(resp);
	}
	
	@Test
	void newTransaction_WithoutDate_Test() throws Exception {

		Optional<MdwTransaction> value = Optional.empty();
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		NewTransactionRequest newTransactionRequest = new NewTransactionRequest();
		newTransactionRequest.setReference("12345A");
		newTransactionRequest.setAccountIban("ES9820385778983000760236");
		newTransactionRequest.setAmount(193.38);
		newTransactionRequest.setFee(3.18);
		newTransactionRequest.setDescription("Restaurant payment");

		NewTransactionResponse resp = transactionServiceImpl.newTransaction(newTransactionRequest );
		assertNotNull(resp);
	}
	
//	@Test(expected = InvalidParameterException.class)
//	public void newTransaction_WithInvalidDate_Test() throws Exception {
//
//		Optional<MdwTransaction> value = Optional.empty();
//		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
//		
//		NewTransactionRequest newTransactionRequest = new NewTransactionRequest();
//		newTransactionRequest.setReference("12345A");
//		newTransactionRequest.setAccountIban("ES9820385778983000760236");
//		newTransactionRequest.setDate("2019-07-16T16:55:42.000");
//		newTransactionRequest.setAmount(193.38);
//		newTransactionRequest.setFee(3.18);
//		newTransactionRequest.setDescription("Restaurant payment");
//
//		NewTransactionResponse resp = transactionServiceImpl.newTransaction(newTransactionRequest );
//		assertNotNull(resp);
//	}	
	
	
	@Test
	void transactionStatus_BusinnewRulesA_Test() throws Exception {
		
		Optional<MdwTransaction> value = Optional.empty();
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_INVALID, resp.getStatus());
	}
	
	@Test
	void transactionStatus_ChannelClient_BusinnewRulesB_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.of(2021, 6, 13, 16, 30, 0, 0, ZoneId.of("Z")));
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.CLIENT);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_SETTLED, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelClient_BusinnewRulesD_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.now());
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.CLIENT);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_PENDING, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelClient_BusinnewRulesF_Test() throws Exception {
		
		ZonedDateTime today = ZonedDateTime.now();
		ZonedDateTime tomorrow = ZonedDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth()+1, today.getHour(), 0, 0, 0, ZoneId.of("Z"));
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(tomorrow);
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.CLIENT);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_FUTURE, resp.getStatus());
	}		
	
	
	
	
	@Test
	void transactionStatus_ChannelAtm_BusinnewRulesB_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.of(2021, 6, 13, 16, 30, 0, 0, ZoneId.of("Z")));
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.ATM);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_SETTLED, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelAtm_BusinnewRulesD_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.now());
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.ATM);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_PENDING, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelAtm_BusinnewRulesF_Test() throws Exception {
		
		ZonedDateTime today = ZonedDateTime.now();
		ZonedDateTime tomorrow = ZonedDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth()+1, today.getHour(), 0, 0, 0, ZoneId.of("Z"));
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(tomorrow);
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.ATM);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_PENDING, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelInternal_BusinnewRulesC_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.of(2021, 6, 13, 16, 30, 0, 0, ZoneId.of("Z")));
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.INTERNAL);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_SETTLED, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelInternal_BusinnewRulesE_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.now());
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.INTERNAL);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_PENDING, resp.getStatus());
	}	
	
	@Test
	void transactionStatus_ChannelInternal_BusinnewRulesH_Test() throws Exception {
		
		ZonedDateTime today = ZonedDateTime.now();
		ZonedDateTime tomorrow = ZonedDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth()+1, today.getHour(), 0, 0, 0, ZoneId.of("Z"));
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(tomorrow);
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		Optional<MdwTransaction> value = Optional.of(mdwTransaction );
		Mockito.when(mdwTransactionRepository.findByReference(Mockito.anyString())).thenReturn(value );
		
		
		TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
		transactionStatusRequest.setReference("12345A");
		transactionStatusRequest.setChannel(ChannelEnum.INTERNAL);
		
		TransactionStatusResponse resp = transactionServiceImpl.transactionStatus(transactionStatusRequest);
		assertNotNull(resp);
		assertEquals(STATUS_FUTURE, resp.getStatus());
	}		
	

	
	@Test
	void searchTransaction_notFound_asc_Test() throws Exception {
		
		String iban = "ES9820385778983000760236";
		String sort = "ascending";
		Integer page = null;
		Integer size = null;
		
		TransactionListResponse resp = transactionServiceImpl.searchTransaction(iban, sort, page, size);
		assertNotNull(resp);
		assertEquals(0, resp.getTotalTransaction());
	}
	
	@Test
	void searchTransaction_notFound_desc_Test() throws Exception {
		
		String iban = "ES9820385778983000760236";
		String sort = "descending";
		Integer page = null;
		Integer size = null;
		
		TransactionListResponse resp = transactionServiceImpl.searchTransaction(iban, sort, page, size);
		assertNotNull(resp);
		assertEquals(0, resp.getTotalTransaction());
	}	
	
	@Test
	void searchTransaction_desc_Test() throws Exception {
		
		MdwTransaction mdwTransaction = new MdwTransaction();
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setReference("12345A");
		mdwTransaction.setAccountIban("ES9820385778983000760236");
		mdwTransaction.setDate(ZonedDateTime.now());
		mdwTransaction.setAmount(193.38);
		mdwTransaction.setFee(3.18);
		mdwTransaction.setDescription("Restaurant payment");
		
		List<MdwTransaction> mdwTransactionList = new ArrayList<MdwTransaction>();
		mdwTransactionList.add(mdwTransaction);
		Mockito.when(mdwTransactionRepository.findAllByAccountIban(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(mdwTransactionList);
		
		
		String iban = "ES9820385778983000760236";
		String sort = "descending";
		Integer page = null;
		Integer size = null;
		
		TransactionListResponse resp = transactionServiceImpl.searchTransaction(iban, sort, page, size);
		assertNotNull(resp);
		assertEquals(1, resp.getTotalTransaction());
	}		
	

}

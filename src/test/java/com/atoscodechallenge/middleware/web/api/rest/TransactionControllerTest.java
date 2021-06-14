package com.atoscodechallenge.middleware.web.api.rest;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.atoscodechallenge.middleware.service.TransactionService;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionRequest;
import com.atoscodechallenge.middleware.web.api.model.NewTransactionResponse;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusRequest;
import com.atoscodechallenge.middleware.web.api.model.TransactionStatusResponse;
import com.atoscodechallenge.middleware.web.rest.TransactionController;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {TransactionController.class, TransactionService.class})
@WebMvcTest
class TransactionControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
    
    private RequestBuilder requestBuilder;
    
    @MockBean 
	private TransactionService transactionService;
    
    @Test
    void shouldDoPostNewTransaction() throws Exception {

    	NewTransactionRequest newTransactionRequest = random(NewTransactionRequest.class);

    	NewTransactionResponse newTransactionResponse = new NewTransactionResponse();
    	newTransactionResponse.setReference("12345A");
    	
    	given(transactionService.newTransaction(any())).willReturn(newTransactionResponse);

        MvcResult result = mockMvc.perform(
            post("/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newTransactionRequest)))
				.andExpect(status().isOk())		
				.andReturn();
        
        verify(transactionService, times(1)).newTransaction(any());
        assertNotNull(result);
        assertNotNull(result.getResponse());

    }
    
    @Test
    @Disabled
    void shouldDoGetTransactionsByIban() throws Exception {
    	
    	String iban = "ES2330089254531933727925";
    	String sort = "ascending";
    	Integer page = 0;
    	Integer size = 10;
    	
    	requestBuilder = MockMvcRequestBuilders.get("/transaction/"+iban)
    			.contentType(MediaType.APPLICATION_JSON)
    	        .accept(MediaType.APPLICATION_JSON);
    	
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	MockHttpServletResponse response = result.getResponse();
    	verify(transactionService).searchTransaction(iban, sort, page, size);
    	assertEquals(HttpStatus.OK.value(), response.getStatus());
    	
    }
    
    @Test
    void shouldDoGetTransactionsStatusByReference() throws Exception {
    	
    	TransactionStatusRequest transactionStatusRequest = random(TransactionStatusRequest.class);

    	TransactionStatusResponse transactionStatusResponse = new TransactionStatusResponse();
    	transactionStatusResponse.setReference("12345A");
    	transactionStatusResponse.setAmount(100.0);
    	transactionStatusResponse.setFee(2.0);
    	transactionStatusResponse.setStatus("PENDING");
    	
    	requestBuilder = MockMvcRequestBuilders.get("/transaction/status")
    			.accept(MediaType.APPLICATION_JSON)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(new ObjectMapper().writeValueAsString(transactionStatusRequest));
    	
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	MockHttpServletResponse response = result.getResponse();
    	verify(transactionService).transactionStatus(transactionStatusRequest);
    	assertEquals(HttpStatus.OK.value(), response.getStatus());
    	
    }    

}

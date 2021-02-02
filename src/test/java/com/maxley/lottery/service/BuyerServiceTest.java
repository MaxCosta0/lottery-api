package com.maxley.lottery;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.maxley.lottery.domain.Buyer;
import com.maxley.lottery.repository.BuyerRepository;
import com.maxley.lottery.service.BuyerService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BuyerServiceTest {
	
	@TestConfiguration
	static class BuyerServiceTestConfiguration {
		
		@Bean
		public BuyerService buyerService() {
			return new BuyerService();
		}
	}
	
	@Autowired
	private BuyerService buyerService;
	
	@MockBean
	private BuyerRepository buyerRepository;
	
	@Before
	public void setup() {
		Buyer buyer = new Buyer(1L, "maxleysoares006@gmail.com");
		
		Mockito.when(buyerRepository.findByEmail(buyer.getEmail())).thenReturn(buyer);
	}
	
	@Test
	public void whenValidEmail_thenBuyerShouldBeFound() {
		String email = "maxleysoares006@gmail.com";
		String email2 = "maxleysoares006@gmail.com";
		Buyer foundBuyer = buyerService.findEmail(email2);
		
		Assertions.assertEquals(foundBuyer.getEmail(), email); 
	}
	
	@Test
	public void whenInvalidEmail_thenShouldReturnNull() {
		String email = "maxleysoares@gmail.com";
		Buyer foundBuyer = buyerService.findEmail(email);
		
		Assertions.assertEquals(foundBuyer.getEmail(), null); 
	}
}

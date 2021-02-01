package com.maxley.lottery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxley.lottery.domain.Buyer;
import com.maxley.lottery.domain.Ticket;
import com.maxley.lottery.repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private TicketService ticketService;
	
	
	public Ticket saveBuyer(Buyer buyer) {
		Buyer existingBuyer = buyerRepository.findByEmail(buyer.getEmail());
		
		if(existingBuyer != null && !buyer.equals(existingBuyer)) {
			return ticketService.saveTicket(existingBuyer);
		}
		
		Ticket drawn = ticketService.saveTicket(buyer);
		buyerRepository.save(buyer);
		
		return drawn;
	}
	
	public Buyer findEmail(String email) {
		return buyerRepository.findByEmail(email); 
	}
}
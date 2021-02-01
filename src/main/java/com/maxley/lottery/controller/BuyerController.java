package com.maxley.lottery.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maxley.lottery.domain.Buyer;
import com.maxley.lottery.domain.Ticket;
import com.maxley.lottery.exception.DomainException;
import com.maxley.lottery.service.BuyerService;

@RestController
@RequestMapping("/buyers")
public class BuyerController {
	
	@Autowired
	public BuyerService buyerService;
	
	@GetMapping("/{email}")
	public List<Ticket> listTickets(@PathVariable String email){
		Buyer buyer = buyerService.findEmail(email);
		if(buyer == null) {
			throw new DomainException("there's no ticket for this email");
		}
		return buyer.getTickets();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Ticket drawTicket(@RequestBody @Valid Buyer buyer) {
		return buyerService.saveBuyer(buyer);
	}
	
}

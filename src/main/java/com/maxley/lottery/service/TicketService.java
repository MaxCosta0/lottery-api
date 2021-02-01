package com.maxley.lottery.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxley.lottery.domain.Buyer;
import com.maxley.lottery.domain.Ticket;
import com.maxley.lottery.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	public String drawTicket() {
		int min = 1;
		int max = 99;
		int quant = 6;
		Random random = new Random();
		boolean repeated = false;
		int drawnNumber = 0;
		List<Integer> drawnList = new ArrayList<Integer>();
		
		for(int i = 0; i < quant; i++) {
			do {
				repeated = false;
				drawnNumber = random.nextInt(max + 1 - min) + min;
				
				for(int k = 0; k < i; k++) {
					if(drawnList.get(k)  == drawnNumber) {
						repeated = true;
						break;
					}
				}
			}while(repeated);
			drawnList.add(drawnNumber);
		}
		
		Collections.sort(drawnList);		
		String result = drawnList.get(0)+ "-" + drawnList.get(1) + "-" + drawnList.get(2) + "-" + drawnList.get(3) + "-" + drawnList.get(4) + "-" + drawnList.get(5);
		
		return result;
	}
	
	public Ticket saveTicket(Buyer buyer) {
		String drawnSequence;
		List<Ticket> tickets = buyer.getTickets();
		boolean repeated;
		
		do {
			repeated = false;
			drawnSequence = drawTicket();
			
			for(Ticket ticket: tickets) {
				if(ticket.getNumber().equals(drawnSequence)) {
					repeated = true;
					break;
				}
			}			
		}while(repeated);
		
		Ticket newTicket = new Ticket(null, drawnSequence, buyer);
		return ticketRepository.save(newTicket);
	
	}
	
}

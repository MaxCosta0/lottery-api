package com.maxley.lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxley.lottery.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}

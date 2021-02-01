package com.maxley.lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxley.lottery.domain.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
	
	Buyer findByEmail(String email);

}



package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.TroubleTicket;


public interface TroubleTicketRepository extends CrudRepository<TroubleTicket, Long>{
	
	TroubleTicket findById(int id);
	TroubleTicket findByTicketNumber(String number);
	List<TroubleTicket> findAll();
}

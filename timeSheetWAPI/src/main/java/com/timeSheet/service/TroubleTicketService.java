package com.timeSheet.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timeSheet.dao.Ticketquery;
import com.timeSheet.dao.TroubleTicketRepository;
import com.timeSheet.model.dbentity.TroubleActivity;
import com.timeSheet.model.dbentity.TroubleTicket;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.ticket.ActivityResponse;
import com.timeSheet.model.ticket.TroubleDate;
import com.timeSheet.model.ticket.TroubleTicketDetail;


@Service
@Transactional
public class TroubleTicketService {


	@Autowired
	TroubleTicketRepository troubleTicketRepository;
	
	@Autowired
	Ticketquery ticketquery;
	

	
	public TroubleTicket saveTicket(TroubleTicket ticket){
		return this.troubleTicketRepository.save(ticket);
	}
	public TroubleTicket getTicketById(int id){
		return this.troubleTicketRepository.findById(id);
	}
	public List<TroubleTicket> getAllTicket(){
		return this.ticketquery.getAllTicket();
	}
	public List<TroubleTicketDetail> getTicketDetail(int assignTo,int roleId,User user){
		return this.ticketquery.getTicketDetail(assignTo,roleId,user);
	}
	public List<ActivityResponse> getActivity(int id){
		return this.ticketquery.getActivity(id);
	}

	public TroubleTicketDetail getTicketDetailById(int id){
		return this.ticketquery.getTicketDetailById(id);
	}
	public TroubleDate getByTroubleDateId(int id){
		return this.ticketquery.getById(id);
	}
}

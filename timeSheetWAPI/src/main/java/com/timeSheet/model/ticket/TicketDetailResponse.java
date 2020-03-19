package com.timeSheet.model.ticket;

import java.util.Date;
import java.util.List;

import com.timeSheet.clib.model.BaseResponse;


public class TicketDetailResponse extends BaseResponse {

	List<TroubleTicketDetail> ticketDetail;
	Date currentDateTime;

	public List<TroubleTicketDetail> getTicketDetail() {
		return ticketDetail;
	}

	public void setTicketDetail(List<TroubleTicketDetail> ticketDetail) {
		this.ticketDetail = ticketDetail;
	}

	public Date getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}
	
	
	
	
	
}

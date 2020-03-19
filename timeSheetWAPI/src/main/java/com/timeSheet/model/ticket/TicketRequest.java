package com.timeSheet.model.ticket;

import java.util.ArrayList;
import java.util.List;

import com.timeSheet.model.dbentity.TroubleActivity;
import com.timeSheet.model.dbentity.TroubleTicket;



public class TicketRequest {

	TroubleTicket troubleTicket;
	TroubleActivity troubleActivity;
	int parentId;

	public TroubleTicket getTroubleTicket() {
		return troubleTicket;
	}

	public void setTroubleTicket(TroubleTicket troubleTicket) {
		this.troubleTicket = troubleTicket;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public TroubleActivity getTroubleActivity() {
		return troubleActivity;
	}

	public void setTroubleActivity(TroubleActivity troubleActivity) {
		this.troubleActivity = troubleActivity;
	}
	
	
	
	
	
	
}

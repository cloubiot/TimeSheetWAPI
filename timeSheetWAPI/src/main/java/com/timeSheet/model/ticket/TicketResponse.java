package com.timeSheet.model.ticket;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.group.MemberListInGroup;


public class TicketResponse extends BaseResponse{

	TroubleDate troubleDate;
	TroubleTicketDetail ticketDetail;
	List<MemberListInGroup> group;
	int slaFlag;
	
	
	
	public TroubleDate getTroubleDate() {
		return troubleDate;
	}
	public void setTroubleDate(TroubleDate troubleDate) {
		this.troubleDate = troubleDate;
	}
	public TroubleTicketDetail getTicketDetail() {
		return ticketDetail;
	}
	public void setTicketDetail(TroubleTicketDetail ticketDetail) {
		this.ticketDetail = ticketDetail;
	}
	public List<MemberListInGroup> getGroup() {
		return group;
	}
	public void setGroup(List<MemberListInGroup> group) {
		this.group = group;
	}
	public int getSlaFlag() {
		return slaFlag;
	}
	public void setSlaFlag(int slaFlag) {
		this.slaFlag = slaFlag;
	}
	
	

	
	
}

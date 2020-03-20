package com.timeSheet.model.ticket;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Status;


public class StatusResponse extends BaseResponse {

	List<Status> getStatus;

	public List<Status> getGetStatus() {
		return getStatus;
	}

	public void setGetStatus(List<Status> getStatus) {
		this.getStatus = getStatus;
	}

	

	
	
}

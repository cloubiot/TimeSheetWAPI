package com.timeSheet.clib.model;

import java.util.List;

import com.timeSheet.model.timesheet.Report;

public class SuccessIDResponse extends BaseResponse{
	long id;
	
	List<Report> report;
	
	
	public SuccessIDResponse(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Report> getReport() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}

	



	

}

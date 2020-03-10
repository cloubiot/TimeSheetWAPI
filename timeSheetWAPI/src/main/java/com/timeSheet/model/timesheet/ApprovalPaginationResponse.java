package com.timeSheet.model.timesheet;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class ApprovalPaginationResponse extends BaseResponse {

	List<Approval> approval;
	
	List<Report> report;
	
	public List<Approval> getApproval() {
		return approval;
	}

	public void setApproval(List<Approval> approval) {
		this.approval = approval;
	}

	public List<Report> getReport() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}
	
	
	
}

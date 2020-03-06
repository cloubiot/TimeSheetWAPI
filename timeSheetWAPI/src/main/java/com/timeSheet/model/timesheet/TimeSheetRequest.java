package com.timeSheet.model.timesheet;


public class TimeSheetRequest {

	Timesheet[] timeSheet;
	
	int userId;
	int orgId;
	int timesheetId;
	int approval;
	
	public Timesheet[] getTimeSheet() {
		return timeSheet;
	}
	public void setTimeSheet(Timesheet[] timeSheet) {
		this.timeSheet = timeSheet;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public int getTimesheetId() {
		return timesheetId;
	}
	public void setTimesheetId(int timesheetId) {
		this.timesheetId = timesheetId;
	}
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	


	
}

package com.timeSheet.model.timesheet;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.timeSheet.model.dbentity.Hrs;

public class TimeSheetRequest {

	Timesheet[] timeSheet;
	
	int userId;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserTd(int userId) {
		this.userId = userId;
	}
	public Timesheet[] getTimeSheet() {
		return timeSheet;
	}
	public void setTimeSheet(Timesheet[] timeSheet) {
		this.timeSheet = timeSheet;
	}
	


	
}

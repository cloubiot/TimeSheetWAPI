package com.timeSheet.model.timesheet;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.timeSheet.model.dbentity.Hrs;

public class TimeSheetRequest {

	Timesheet[] timeSheet;
	
	int userTd;
	
	
	public int getUserTd() {
		return userTd;
	}
	public void setUserTd(int userTd) {
		this.userTd = userTd;
	}
	public Timesheet[] getTimeSheet() {
		return timeSheet;
	}
	public void setTimeSheet(Timesheet[] timeSheet) {
		this.timeSheet = timeSheet;
	}
	


	
}

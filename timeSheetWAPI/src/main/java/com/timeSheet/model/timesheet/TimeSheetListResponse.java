package com.timeSheet.model.timesheet;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class TimeSheetListResponse extends BaseResponse{

	List<TimeSheetList> timeSheet;
	
	
	public List<TimeSheetList> getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(List<TimeSheetList> timeSheet) {
		this.timeSheet = timeSheet;
	}

	
	
}

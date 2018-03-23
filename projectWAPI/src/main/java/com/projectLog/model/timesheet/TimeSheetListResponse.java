package com.projectLog.model.timesheet;

import java.util.List;

import com.projectLog.clib.model.BaseResponse;

public class TimeSheetListResponse extends BaseResponse{

	List<TimeSheetList> timeSheet;

	public List<TimeSheetList> getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(List<TimeSheetList> timeSheet) {
		this.timeSheet = timeSheet;
	}
	
	
}

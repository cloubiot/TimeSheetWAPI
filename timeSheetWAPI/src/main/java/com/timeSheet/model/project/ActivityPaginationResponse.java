package com.timeSheet.model.project;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.timesheet.Activities;

public class ActivityPaginationResponse extends BaseResponse{
	
	List<Activities> activity;

	public List<Activities> getActivity() {
		return activity;
	}

	public void setActivity(List<Activities> activity) {
		this.activity = activity;
	}

	
	
	

}

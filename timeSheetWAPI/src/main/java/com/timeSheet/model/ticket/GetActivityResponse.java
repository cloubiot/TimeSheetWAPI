package com.timeSheet.model.ticket;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.TroubleActivity;


public class GetActivityResponse extends BaseResponse{

	List<TroubleActivity> activity;
	List<ActivityResponse> activityResponse;

	public List<TroubleActivity> getActivity() {
		return activity;
	}

	public void setActivity(List<TroubleActivity> activity) {
		this.activity = activity;
	}

	public List<ActivityResponse> getActivityResponse() {
		return activityResponse;
	}

	public void setActivityResponse(List<ActivityResponse> activityResponse) {
		this.activityResponse = activityResponse;
	}

	
	
	
}

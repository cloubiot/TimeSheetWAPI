package com.timeSheet.model.project;

import com.timeSheet.model.timesheet.Activities;

public class ActivityPaginationRequest {

	int value;
    Activities activityDetail;
    int orgId;
    String activity;
    int userId;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Activities getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(Activities activityDetail) {
		this.activityDetail = activityDetail;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}

package com.timeSheet.model.timesheet;

import com.timeSheet.clib.model.BaseResponse;

public class HoursResponse extends BaseResponse{

	
	String hours;
	int projectId;
	int userId;

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}

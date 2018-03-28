package com.timeSheet.model.project;

import com.timeSheet.clib.model.BaseResponse;

public class GetCountResponse extends BaseResponse{

	long projectCount;
	long userCount;
	public long getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(long projectCount) {
		this.projectCount = projectCount;
	}
	public long getUserCount() {
		return userCount;
	}
	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}
	
	
}

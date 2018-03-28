package com.timeSheet.model.usermgmt;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class UserHourResponse extends BaseResponse{

	List<UserHour> userHour;

	public List<UserHour> getUserHour() {
		return userHour;
	}

	public void setUserHour(List<UserHour> userHour) {
		this.userHour = userHour;
	}
	
	
	
}

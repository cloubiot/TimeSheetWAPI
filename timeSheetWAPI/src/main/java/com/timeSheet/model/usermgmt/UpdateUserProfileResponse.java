package com.timeSheet.model.usermgmt;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.User;

public class UpdateUserProfileResponse extends BaseResponse{

	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

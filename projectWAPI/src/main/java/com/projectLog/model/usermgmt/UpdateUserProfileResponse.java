package com.projectLog.model.usermgmt;

import com.projectLog.model.dbentity.User;
import com.projectLog.clib.model.BaseResponse;

public class UpdateUserProfileResponse extends BaseResponse{

	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

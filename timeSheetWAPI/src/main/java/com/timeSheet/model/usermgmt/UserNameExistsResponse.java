package com.timeSheet.model.usermgmt;

import com.timeSheet.clib.model.BaseResponse;

public class UserNameExistsResponse extends BaseResponse{

	boolean userNameExists;

	public boolean isUserNameExists() {
		return userNameExists;
	}

	public void setUserNameExists(boolean userNameExists) {
		this.userNameExists = userNameExists;
	}

}

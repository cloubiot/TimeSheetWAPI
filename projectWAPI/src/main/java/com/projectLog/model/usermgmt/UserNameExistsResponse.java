package com.projectLog.model.usermgmt;

import com.projectLog.clib.model.BaseResponse;

public class UserNameExistsResponse extends BaseResponse{

	boolean userNameExists;

	public boolean isUserNameExists() {
		return userNameExists;
	}

	public void setUserNameExists(boolean userNameExists) {
		this.userNameExists = userNameExists;
	}

}

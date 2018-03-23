package com.projectLog.model.usermgmt;

import com.projectLog.clib.model.BaseResponse;

public class ForgotUsernameResponse extends BaseResponse{

	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

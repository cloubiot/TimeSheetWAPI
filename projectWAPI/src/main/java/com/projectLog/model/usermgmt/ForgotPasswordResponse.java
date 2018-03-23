package com.projectLog.model.usermgmt;

import com.projectLog.clib.model.BaseResponse;

public class ForgotPasswordResponse extends BaseResponse{

	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}

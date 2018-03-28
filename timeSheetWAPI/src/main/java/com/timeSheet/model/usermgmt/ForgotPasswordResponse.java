package com.timeSheet.model.usermgmt;

import com.timeSheet.clib.model.BaseResponse;

public class ForgotPasswordResponse extends BaseResponse{

	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}

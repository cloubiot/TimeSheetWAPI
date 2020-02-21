package com.timeSheet.clib.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {
	@JsonProperty("isSuccess")
	boolean isSuccess=true;
	String userErrorMsg;
	
	@JsonIgnore
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUserErrorMsg() {
		return userErrorMsg;
	}

	public void setUserErrorMsg(String userErrorMsg) {
		this.userErrorMsg = userErrorMsg;
	}

	
}

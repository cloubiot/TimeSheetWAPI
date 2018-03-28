package com.timeSheet.clib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {
	@JsonProperty("isSuccess")
	boolean isSuccess=true;
	
	@JsonIgnore
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	
}

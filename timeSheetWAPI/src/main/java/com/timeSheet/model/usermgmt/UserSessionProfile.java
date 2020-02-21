package com.timeSheet.model.usermgmt;

import java.io.Serializable;

public class UserSessionProfile implements Serializable{

	int id;
	long adminId;
	String secureToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public String getSecureToken() {
		return secureToken;
	}

	public void setSecureToken(String secureToken) {
		this.secureToken = secureToken;
	}
	
	
}

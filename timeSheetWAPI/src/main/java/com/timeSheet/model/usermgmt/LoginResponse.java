package com.timeSheet.model.usermgmt;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.User;

public class LoginResponse extends BaseResponse{

	User user;
	long roleId;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	
}

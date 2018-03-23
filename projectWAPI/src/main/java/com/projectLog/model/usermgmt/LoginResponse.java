package com.projectLog.model.usermgmt;

import com.projectLog.model.dbentity.User;
import com.projectLog.clib.model.BaseResponse;

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

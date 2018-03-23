package com.projectLog.model.usermgmt;


import com.projectLog.clib.model.BaseResponse;
import com.projectLog.model.dbentity.User;

public class UserProfile extends BaseResponse {

	User user;
	int roleId;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
}

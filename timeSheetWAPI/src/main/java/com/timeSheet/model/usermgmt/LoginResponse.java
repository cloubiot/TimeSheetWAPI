package com.timeSheet.model.usermgmt;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.User;

public class LoginResponse extends BaseResponse{

//	User user;
	List<User> user;
	long roleId;
	long orgId;

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public long getRoleId() {
		return roleId;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	
}

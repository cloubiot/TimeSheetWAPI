package com.projectLog.model.usermgmt;

import java.util.List;

import com.projectLog.model.dbentity.User;
import com.projectLog.clib.model.BaseResponse;

public class UserListResponse extends BaseResponse{

	List<UserWithRole> user;

	public List<UserWithRole> getUser() {
		return user;
	}

	public void setUser(List<UserWithRole> user) {
		this.user = user;
	}
	
	
}

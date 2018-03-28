package com.timeSheet.model.usermgmt;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.User;

public class UserListResponse extends BaseResponse{

	List<UserWithRole> user;

	public List<UserWithRole> getUser() {
		return user;
	}

	public void setUser(List<UserWithRole> user) {
		this.user = user;
	}
	
	
}

package com.timeSheet.model.usermgmt;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.UserRole;

public class RoleResponse extends BaseResponse{

	List<UserRole> userRole;

	public List<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	
}

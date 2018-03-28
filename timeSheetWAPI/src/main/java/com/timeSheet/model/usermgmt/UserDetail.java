package com.timeSheet.model.usermgmt;

import com.timeSheet.model.dbentity.User;

public class UserDetail extends User{

	int roleId;
	String desc;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

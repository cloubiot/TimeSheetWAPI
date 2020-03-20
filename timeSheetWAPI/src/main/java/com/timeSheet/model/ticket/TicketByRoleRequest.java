package com.timeSheet.model.ticket;

import com.timeSheet.model.dbentity.User;

public class TicketByRoleRequest {

	int roleId;
	User user;
	int orgId;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	
	
}

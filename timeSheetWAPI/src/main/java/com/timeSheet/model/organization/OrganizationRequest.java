package com.timeSheet.model.organization;

import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.dbentity.User;

public class OrganizationRequest {

	Organization org;
	User user;

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}

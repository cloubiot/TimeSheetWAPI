package com.timeSheet.model.usermgmt;

public class SearchUserDetailRequest {

	UserDetail userDetail;
    int orgId;
	
	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	
}

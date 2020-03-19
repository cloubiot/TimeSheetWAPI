package com.timeSheet.model.usermgmt;

public class AddCustomerRequest {

	AddCustomer addCustomer;
	String loginUser;
	int orgId;
	int userId;
	
	public AddCustomer getAddCustomer() {
		return addCustomer;
	}
	public void setAddCustomer(AddCustomer addCustomer) {
		this.addCustomer = addCustomer;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}

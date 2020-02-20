package com.timeSheet.model.organization;

public class SearchOrgRequest {

	OrganizationDetails orgDetail;
	String name;
	int value;
	
	public OrganizationDetails getOrgDetail() {
		return orgDetail;
	}
	public void setOrgDetail(OrganizationDetails orgDetail) {
		this.orgDetail = orgDetail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}

package com.timeSheet.model.organization;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class SearchOrgResponse extends BaseResponse{

	List<OrganizationDetails> organizationDetails;

	public List<OrganizationDetails> getOrganizationDetails() {
		return organizationDetails;
	}

	public void setOrganizationDetails(List<OrganizationDetails> organizationDetails) {
		this.organizationDetails = organizationDetails;
	}
	
	
}

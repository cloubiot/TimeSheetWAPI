package com.timeSheet.model.organization;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Organization;

public class OrganizationResponse extends BaseResponse{

	Organization organization;
	List<OrganizationDetails> organizationList;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<OrganizationDetails> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<OrganizationDetails> organizationList) {
		this.organizationList = organizationList;
	}

	
	
	
}

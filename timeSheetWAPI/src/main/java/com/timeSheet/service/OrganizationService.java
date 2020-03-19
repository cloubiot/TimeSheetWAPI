package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.OrganizationQuery;
import com.timeSheet.dao.OrganizationRepository;
import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.organization.OrganizationDetails;

@Service
@Transactional
public class OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	OrganizationQuery organizationQuery;
	
	public Organization saveOrg(Organization organization){
		return this.organizationRepository.save(organization);
	}
	public Organization getById(int orgId) {
		return this.organizationRepository.findById(orgId);
	}
	public List<OrganizationDetails> getOrganization(){
		return this.organizationQuery.getOrganization();
	}
	public List<OrganizationDetails> searchOrg(String name){
		return this.organizationQuery.searchOrg(name);
	}
	public List<OrganizationDetails> orgPagination(int from,int to,String name){
		return this.organizationQuery.orgPagination(from,to,name);
	}
	
}

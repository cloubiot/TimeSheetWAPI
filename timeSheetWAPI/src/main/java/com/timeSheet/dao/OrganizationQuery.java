package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.model.organization.OrganizationDetails;
import com.timeSheet.model.project.ProjectDetail;

@Service
@Transactional
public class OrganizationQuery {

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	
	public List<OrganizationDetails> getOrganization(){
		String query = "select o.id,o.name,o.created_date,o.type,o.address,(select count(*) from projects where org_id = o.id) as projectCnt,(select count(*) from user where org_id = o.id) as userCnt, " 
				+"(select count(*) from activities where org_id = o.id) as activityCnt from organization as o " 
				+"group by o.id " ;
		List<OrganizationDetails> getOrganization = jdbcTemplate.query(query, new BeanPropertyRowMapper(OrganizationDetails.class));
		return getOrganization;
	}
	
	public List<OrganizationDetails> searchOrg(String name){
		String query = "select o.id,o.name,o.created_date,(select count(*) from projects where org_id = o.id) as projectCnt,(select count(*) from user where org_id = o.id) as userCnt " 
				+"from organization as o where o.name like '%"+name+"%' " 
				+"group by o.id " ;
		List<OrganizationDetails> searchOrg = jdbcTemplate.query(query, new BeanPropertyRowMapper(OrganizationDetails.class));
		return searchOrg;
	}
	
	public List<OrganizationDetails> orgPagination(int from,int to,String name){
		if(name == null) {
			name = "";
		}
		String query = "select o.id,o.name,o.created_date,(select count(*) from projects where org_id = o.id) as projectCnt,(select count(*) from user where org_id = o.id) as userCnt " 
				+"from organization as o where o.name like '%"+name+"%' " 
				+"group by o.id limit "+from +" ,"+to ;
		List<OrganizationDetails> orgPagination = jdbcTemplate.query(query, new BeanPropertyRowMapper(OrganizationDetails.class));
		return orgPagination;
	}
	
}

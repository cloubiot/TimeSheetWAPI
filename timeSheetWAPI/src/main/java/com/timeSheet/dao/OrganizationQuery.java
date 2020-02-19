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
		String query = "select o.id,o.name,o.created_date,(select count(*) from projects where org_id = o.id) as projectCnt,(select count(*) from user where org_id = o.id) as userCnt " 
				+"from organization as o " 
				+"group by o.id " ;
		List<OrganizationDetails> getOrganization = jdbcTemplate.query(query, new BeanPropertyRowMapper(OrganizationDetails.class));
		return getOrganization;
	}
	
}

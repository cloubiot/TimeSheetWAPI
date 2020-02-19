package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.Organization;

public interface OrganizationRepository extends CrudRepository<Organization,Long>{

	Organization findByName(String name);
	Organization findBySite(String name);
	Organization findById(int id);
	List<Organization> findAll();
}

package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.UserRoleMapping;

public interface UserRoleMappingRepository extends CrudRepository<UserRoleMapping,Long> {
	
	UserRoleMapping findByUserId(int id);

}

package com.projectLog.dao;

import org.springframework.data.repository.CrudRepository;

import com.projectLog.model.dbentity.UserRoleMapping;

public interface UserRoleMappingRepository extends CrudRepository<UserRoleMapping,Long> {
	
	UserRoleMapping findByUserId(int id);

}

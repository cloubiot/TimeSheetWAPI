package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>{

	List<UserRole> findAll();
}

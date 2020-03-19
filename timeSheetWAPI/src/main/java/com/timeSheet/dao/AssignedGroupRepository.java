package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.AssignedGroups;

public interface AssignedGroupRepository extends CrudRepository<AssignedGroups,Long>{

	AssignedGroups findById(int id);
	List<AssignedGroups> findAll();
}

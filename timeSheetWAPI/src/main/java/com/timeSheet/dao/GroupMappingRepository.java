package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.GroupMapping;

public interface GroupMappingRepository extends CrudRepository<GroupMapping,Long>{

	GroupMapping findByUserId(int id);
	GroupMapping deleteByUserId(int id);
}

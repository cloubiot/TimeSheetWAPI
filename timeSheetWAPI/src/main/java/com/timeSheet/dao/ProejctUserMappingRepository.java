package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.ProjectUserMapping;

public interface ProejctUserMappingRepository extends CrudRepository<ProjectUserMapping, Long>{

	ProjectUserMapping findByProjectIdAndUserId(int projectId,int userId);
	ProjectUserMapping deleteByProjectIdAndUserId(int projectId, int userId);
}

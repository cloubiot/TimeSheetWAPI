package com.projectLog.dao;

import org.springframework.data.repository.CrudRepository;

import com.projectLog.model.dbentity.ProjectUserMapping;

public interface ProejctUserMappingRepository extends CrudRepository<ProjectUserMapping, Long>{

	ProjectUserMapping findByProjectIdAndUserId(int projectId,int userId);
}

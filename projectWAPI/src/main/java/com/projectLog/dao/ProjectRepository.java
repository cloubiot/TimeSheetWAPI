package com.projectLog.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.projectLog.model.dbentity.Projects;

public interface ProjectRepository extends CrudRepository<Projects,Long>{

	Projects findById(int id);
	List<Projects> findAll();
}

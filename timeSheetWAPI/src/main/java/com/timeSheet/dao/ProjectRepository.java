package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.Projects;

public interface ProjectRepository extends CrudRepository<Projects,Long>{

	Projects findById(int id);
	List<Projects> findAll();
	
}

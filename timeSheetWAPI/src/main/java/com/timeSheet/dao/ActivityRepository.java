package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Activities;


public interface ActivityRepository extends CrudRepository<Activities, Long>{
	
	Activities findById(int activityId);

}

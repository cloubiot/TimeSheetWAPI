package com.timeSheet.dao;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Timesheet;



public interface TimeSheetRepository extends CrudRepository<Timesheet, Long>{
	
//	Timesheet findById(String project,String activity,Date date,int userId);


	Timesheet findById(int id);


}

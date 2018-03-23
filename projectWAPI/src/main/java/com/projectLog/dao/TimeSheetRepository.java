package com.projectLog.dao;

import org.springframework.data.repository.CrudRepository;

import com.projectLog.model.timesheet.Timesheet;



public interface TimeSheetRepository extends CrudRepository<Timesheet, Long>{

}

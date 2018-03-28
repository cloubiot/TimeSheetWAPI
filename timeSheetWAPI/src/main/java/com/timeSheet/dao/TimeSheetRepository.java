package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Timesheet;



public interface TimeSheetRepository extends CrudRepository<Timesheet, Long>{

}

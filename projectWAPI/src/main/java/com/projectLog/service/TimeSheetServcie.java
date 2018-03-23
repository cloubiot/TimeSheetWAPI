package com.projectLog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectLog.dao.TimeSheetQuery;
import com.projectLog.dao.TimeSheetRepository;
import com.projectLog.model.timesheet.HoursResponse;
import com.projectLog.model.timesheet.TimeSheetList;
import com.projectLog.model.timesheet.Timesheet;

@Service
@Transactional
public class TimeSheetServcie {

	@Autowired
	TimeSheetRepository timeSheetRepository;
	
	@Autowired
	TimeSheetQuery timeSheetQuery;
	
	public Timesheet saveTimeSheet(Timesheet timesheet){
		return this.timeSheetRepository.save(timesheet);
	}
	
	public List<TimeSheetList> getTimeSheetByUserId(int userId,int projectId,int roleId){
		return this.timeSheetQuery.getTimeSheetByUserId(userId,projectId,roleId);
	}
	
	public String getHrsById(int projectId,int userId){
		return this.timeSheetQuery.getHrsById(projectId, userId);
	}
}

package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.TimeSheetQuery;
import com.timeSheet.dao.TimeSheetRepository;
import com.timeSheet.model.timesheet.HoursResponse;
import com.timeSheet.model.timesheet.TimeSheetList;
import com.timeSheet.model.timesheet.Timesheet;

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
	
	public List<TimeSheetList> getTimeSheetPagination(int userId, int projectId, int roleId, int from, int to){
		return this.timeSheetQuery.getTimeSheetPagination(userId, projectId, roleId, from, to);
	}
}

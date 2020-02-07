package com.timeSheet.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.ReportRepository;
import com.timeSheet.dao.TimeSheetQuery;
import com.timeSheet.dao.TimeSheetRepository;
import com.timeSheet.dao.UserQuery;
import com.timeSheet.model.timesheet.Activity;
import com.timeSheet.model.timesheet.HoursResponse;
import com.timeSheet.model.timesheet.Project;
import com.timeSheet.model.timesheet.Report;
import com.timeSheet.model.timesheet.ReportList;
import com.timeSheet.model.timesheet.Reportview;
import com.timeSheet.model.timesheet.TimeSheetList;
import com.timeSheet.model.timesheet.Timesheet;
import com.timeSheet.model.usermgmt.UserWithRole;


@Service
@Transactional
public class TimeSheetServcie {

	@Autowired
	TimeSheetRepository timeSheetRepository;
	
	@Autowired
	TimeSheetQuery timeSheetQuery;
	
	@Autowired
	ReportRepository reportRepository;

	@Autowired
	UserQuery userQuery;
	
	 
	
	public Timesheet saveTimeSheet(Timesheet timesheet){
		return this.timeSheetRepository.save(timesheet);
	}
	public Timesheet getById(int id){
		return this.timeSheetRepository.findById(id);
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
	
	public List<Report> getReport(int  userId){
		return this.timeSheetQuery.getReport(userId);
	}
	public List<Reportview> getReportview(int userId,Date date){
		return this.timeSheetQuery.getReportview(userId,date);
	}
	
	public List<Timesheet> findUpdateHrs(String project,String activity,Date date,int userId){
		return this.timeSheetQuery.findUpdateHrs(project,activity,date,userId);
	}
	
	public List<Timesheet> findActivity(String project,String activity,Date date,int userId){
		return this.timeSheetQuery.findActivity(project,activity,date,userId);
	}

	public List<Project> getProject() {
		return this.timeSheetQuery.getProject();
		
	}
	public List<ReportList> getReportlist(int id,Date date1,Date date2 ){
		return this.timeSheetQuery.getReportlist(id,date1,date2);
	}
	
	public List<Activity> getActivity() {
		return this.timeSheetQuery.getActivity();
		
	}
}

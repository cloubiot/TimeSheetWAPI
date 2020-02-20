package com.timeSheet.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timeSheet.clib.model.SuccessIDResponse;
import com.timeSheet.clib.util.DateTimeUtil;
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.timesheet.HoursResponse;
import com.timeSheet.model.timesheet.Project;
import com.timeSheet.model.timesheet.Report;
import com.timeSheet.model.timesheet.ReportList;
import com.timeSheet.model.timesheet.ReportRequest;
import com.timeSheet.model.timesheet.ReportResponse;
import com.timeSheet.model.timesheet.Reportview;
import com.timeSheet.model.timesheet.TimeSheetList;
import com.timeSheet.model.timesheet.TimeSheetListRequest;
import com.timeSheet.model.timesheet.TimeSheetListResponse;
import com.timeSheet.model.timesheet.TimeSheetRequest;
import com.timeSheet.model.timesheet.Timesheet;
import com.timeSheet.model.usermgmt.UserListResponse;
import com.timeSheet.model.usermgmt.UserWithRole;
import com.timeSheet.service.ProjectService;
import com.timeSheet.service.TimeSheetServcie;


@RestController
@RequestMapping("/timesheet")
@CrossOrigin( maxAge = 3600)
public class TimeSheetController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetController.class);
	
	@Autowired
	TimeSheetServcie timeSheetService;
	@Autowired
	ProjectService projectService;
	
//	@RequestMapping(method = RequestMethod.POST, value="/updateTimeSheet")
//	public SuccessIDResponse updateTimeSheet(@RequestBody TimeSheetRequest request){
//		SuccessIDResponse response = new SuccessIDResponse();
//		try{
//			System.out.println(" timesheet request "+JSONUtil.toJson(request));
//			Timesheet timesheet = new Timesheet();
//			timesheet.setActivity(request.getTimeSheet().getActivity());
//			timesheet.setCheckIn(request.getTimeSheet().getCheckIn());
//			timesheet.setCheckOut(request.getTimeSheet().getCheckOut());
////			timesheet.setNoOfHrs(request);
//			timesheet.setProjectId(request.getTimeSheet().getProjectId());
//			timesheet.setUserId(request.getTimeSheet().getUserId());
//			timesheet.setUpdatedDate(request.getTimeSheet().getUpdatedDate());
//			timesheet.setNoOfHrs(request.getTimeSheet().getNoOfHrs());
//			timeSheetService.saveTimeSheet(timesheet);
////			DateFormat sdf = new SimpleDateFormat("HH:mm");
////			Date date = sdf.parse(request.getTimeSheet().getNoOfHrs());
//			
//			
//			String totalhrs = timeSheetService.getHrsById(timesheet.getProjectId(), 0);
//			System.out.println("HRs "+totalhrs);
//			String hrsPerUser = timeSheetService.getHrsById(timesheet.getProjectId(), timesheet.getUserId());
//			Projects project = projectService.getProjectById(timesheet.getProjectId());
//			project.setNoOfHrs(totalhrs);
//			projectService.addProject(project);
//			
//			ProjectUserMapping projectMapping = projectService.getByProjectAndUserId(timesheet.getProjectId(), timesheet.getUserId());
//			projectMapping.setHoursPerProject(hrsPerUser);
//			projectService.saveMapping(projectMapping);
//			
//			logger.info("timesheet ");
//		}
//		catch(Exception e){
//			logger.error("Time sheet fail",e);
//			response.setSuccess(false);
//			
//		}
//		return response;
//	}
	
	@RequestMapping(method = RequestMethod.POST, value="timesheetReport")
	public SuccessIDResponse timesheetReport(@RequestBody TimeSheetRequest request){
	SuccessIDResponse response = new SuccessIDResponse();
	System.out.println("++++"+JSONUtil.toJson(request));
	try {
		
		for( int i = 0;i < request.getTimeSheet().length;i++) {
			Timesheet editTimesheet = timeSheetService.getById(request.getTimeSheet()[i].getId());
//			System.out.println(JSONUtil.toJson("#####"+editTimesheet.getId()));
			if(editTimesheet == null) {
	        Timesheet timesheet = new Timesheet();
			timesheet.setDate(request.getTimeSheet()[i].getDate());
			timesheet.setProjectId(request.getTimeSheet()[i].getProjectId());
			timesheet.setActivityId(request.getTimeSheet()[i].getActivityId());
			timesheet.setTask(request.getTimeSheet()[i].getTask());
			timesheet.setHrs(request.getTimeSheet()[i].getHrs());
			timesheet.setUserId(request.getUserId());
			timesheet.setCreationDate(new Date());
			timesheet.setUpdatedDate(new Date());
			timeSheetService.saveTimeSheet(timesheet);
			
//			System.out.println("####"+JSONUtil.toJson(timesheet));
			}
			else {
				
				editTimesheet.setDate(request.getTimeSheet()[i].getDate());
				editTimesheet.setProjectId(request.getTimeSheet()[i].getProjectId());
				editTimesheet.setActivityId(request.getTimeSheet()[i].getActivityId());
				editTimesheet.setTask(request.getTimeSheet()[i].getTask());
				editTimesheet.setHrs(request.getTimeSheet()[i].getHrs());
				editTimesheet.setUserId(request.getUserId());
				editTimesheet.setUpdatedDate(new Date());
				timeSheetService.saveTimeSheet(editTimesheet);
			}

	}
	List<Report> timereport = timeSheetService.getReport(request.getUserId());
	response.setReport(timereport);
	}
	catch(Exception e){
		logger.error("Time sheet fail",e);
		response.setSuccess(false);
		
	}
	return response;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/getReport")
	public ReportResponse getReport(@RequestBody ReportRequest request){
		ReportResponse response = new ReportResponse();
		try{
			List<Report> reportview = timeSheetService.getReport(request.getUserId());
			response.setReport(reportview);
//			System.out.println(JSONUtil.toJson(reportview));
			logger.info("employee Report");
		}
		catch(Exception e){
			logger.error("List failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/getProject/{orgId}")
	public ReportResponse getProject(@PathVariable int orgId) {
		ReportResponse response = new ReportResponse();
		try {
			List<Project> project = timeSheetService.getProject(orgId);
			response.setProject(project);
			logger.info("project list");
	}
		catch(Exception e) {
			logger.error("List failed",e);
			response.setSuccess(false);
		}
	return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getReportview")
	public ReportResponse getReportview(@RequestBody ReportRequest request){
		ReportResponse response = new ReportResponse();
		try{
			List<Reportview> reportviews = timeSheetService.getReportview(request.getUserId(),request.getDate());
			response.setReportview(reportviews);
//			System.out.println(JSONUtil.toJson(reportviews));
			logger.info("employee Report");
		}
		catch(Exception e){
			logger.error("List failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getReportlist")
	public ReportResponse getReportlist(@RequestBody ReportRequest request) {
		ReportResponse response = new ReportResponse();
//		System.out.println(" @@@@@@ "+JSONUtil.toJson(request));
		try {
			List<ReportList> reportlist =  timeSheetService.getReportlist(request.getUserName(),request.getDate1(),request.getDate2(),request.getProjectName(),request.getActivityName(),request.getOrgId());
			response.setReportlist(reportlist);
//			System.out.println(" @@@@@@ "+JSONUtil.toJson(reportlist));
			logger.info("project list");
		}
		catch(Exception e) {
			logger.error("List failed",e);
			response.setSuccess(false);
		}
	return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/getActivity/{orgId}")
	public ReportResponse getActivity(@PathVariable int orgId) {
		ReportResponse response = new ReportResponse();
		try {
			List<Activities> activity = timeSheetService.getActivity(orgId);
			response.setActivity(activity);
			logger.info("project list");
	}
		catch(Exception e) {
			logger.error("List failed",e);
			response.setSuccess(false);
		}
	return response;
}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/getTimeSheetByUserId")
	public TimeSheetListResponse getTimeSheetByUserId(@RequestBody TimeSheetListRequest request){
		TimeSheetListResponse response = new TimeSheetListResponse();
		try{
			List<TimeSheetList> timeSheet = timeSheetService.getTimeSheetByUserId(request.getUserId(),request.getProjectId(),request.getRoleId());
			response.setTimeSheet(timeSheet);
			logger.info("List of timesheet by id");
		}
		catch(Exception e){
			logger.error("Time sheet fail",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getHrsById")
	public HoursResponse getHrsById(@RequestBody TimeSheetListRequest request){
		HoursResponse response = new HoursResponse();
		try{
			String hrs = timeSheetService.getHrsById(request.getProjectId(), request.getUserId());
			
			logger.info("Total hrs by id");
		}
		catch(Exception e){
			logger.error("Total hrs fail",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getTimeSheetPagination")
	public TimeSheetListResponse getTimeSheetPagination(@RequestBody TimeSheetListRequest request){
		TimeSheetListResponse response = new TimeSheetListResponse();
		try{
			int from=1;
			int to=10;
			for(int i=1;i<=request.getValue();i++){
				if(i==1){
					from=0;
					to=10;
				}
				else{
					from+=10;
					to+=10;
				}
			}
			List<TimeSheetList> timeSheet = timeSheetService.getTimeSheetPagination(request.getUserId(),
											request.getProjectId(),request.getRoleId(), from, to);
			response.setTimeSheet(timeSheet);
			logger.info("List of timesheet by id");
		}
		catch(Exception e){
			logger.error("Time sheet fail",e);
			response.setSuccess(false);
		}
		return response;
		
	}
}

package com.timeSheet.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.clib.model.SuccessIDResponse;
import com.timeSheet.clib.util.AuthUtil;
import com.timeSheet.clib.util.DateTimeUtil;
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.clib.util.UuidProfile;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.project.ActivityPaginationRequest;
import com.timeSheet.model.project.ActivityPaginationResponse;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.timesheet.Approval;
import com.timeSheet.model.timesheet.ApprovalPaginationRequest;
import com.timeSheet.model.timesheet.ApprovalPaginationResponse;
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
import com.timeSheet.model.usermgmt.UserSessionProfile;
import com.timeSheet.model.usermgmt.UserWithRole;
import com.timeSheet.service.ProjectService;
import com.timeSheet.service.TimeSheetServcie;
import com.timeSheet.service.UserMgmtService;


@RestController
@RequestMapping("/timesheet")
@CrossOrigin( maxAge = 3600)
public class TimeSheetController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetController.class);
	
	@Autowired
	TimeSheetServcie timeSheetService;
	
	
	@Autowired
	ProjectService projectService;
	

	@Autowired
	UserMgmtService userMgmtService;
	
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
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/addNewRecord")
	public SuccessIDResponse timesheetReport(@RequestBody TimeSheetRequest request,HttpServletRequest servletRequest){
	SuccessIDResponse response = new SuccessIDResponse();
	getActivity(servletRequest);
		if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
			return response;
		}
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
			timesheet.setActive(1);
			timesheet.setCreationDate(new Date());
			timesheet.setUpdatedDate(new Date());
			timeSheetService.saveTimeSheet(timesheet);
			Approval approval = new Approval();
			approval.setUserId(request.getUserId());
			approval.setOrgId(request.getOrgId());
			approval.setTimesheetId(timesheet.getId());
			approval.setApproval(0);
			timeSheetService.saveApproval(approval);
//			System.out.println("####"+JSONUtil.toJson(timesheet));
			}
			else {
				
				editTimesheet.setDate(request.getTimeSheet()[i].getDate());
				editTimesheet.setProjectId(request.getTimeSheet()[i].getProjectId());
				editTimesheet.setActivityId(request.getTimeSheet()[i].getActivityId());
				editTimesheet.setTask(request.getTimeSheet()[i].getTask());
				editTimesheet.setHrs(request.getTimeSheet()[i].getHrs());
				editTimesheet.setUserId(request.getUserId());
				if(editTimesheet.getActive() != 0) {
				   editTimesheet.setActive(1);
				}
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
//			for(Report abc :reportview ) {
//				SimpleDateFormat aaa =new SimpleDateFormat("MM/dd/yyyy");
//				 String strDate = aaa.format(abc.getDate().replace("-", "/")); 
//				Date vvv = aaa.parse(abc.getDate().replace("-", "/"));
//				Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(abc.getDate().replace("-", "/")); 
//				
//			System.out.println("####"+strDate);
//			}
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
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getProject")
	public ReportResponse getProject(@RequestBody TimeSheetListRequest request,HttpServletRequest servletRequest) {
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try {
			List<Project> project = timeSheetService.getProject(request.getOrgId());
			response.setProject(project);
			logger.info("project list");
	}
		catch(Exception e) {
			logger.error("List failed",e);
			response.setSuccess(false);
		}
	return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getTimeSheet")
	public ReportResponse getReportview(@RequestBody ReportRequest request,HttpServletRequest servletRequest){
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
			return response;
		}
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
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getReportlist")
	public ReportResponse getReportlist(@RequestBody ReportRequest request,HttpServletRequest servletRequest) {
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getId(),servletRequest)) {
				return response;
			}
			return response;
		}
//		System.out.println(" @@@@@@ "+JSONUtil.toJson(request));
		try {
			String userName ="";
			if(Integer.parseInt(request.getUserName()) != 0) {
			  User user = userMgmtService.getUserById(Integer.parseInt(request.getUserName()));
			  userName = user.getUserName();
			}
			List<ReportList> reportlist =  timeSheetService.getReportlist(userName,request.getDate1(),request.getDate2(),request.getProjectName(),request.getActivityName(),request.getOrgId());
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
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getActivity")
	public ReportResponse getActivity(@RequestBody TimeSheetListRequest request) {
		ReportResponse response = new ReportResponse();
		try {
			List<Activities> activity = timeSheetService.getActivity(request.getOrgId());
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/updateApproval")
	public ReportResponse updateApproval(@RequestBody TimeSheetListRequest request,HttpServletRequest servletRequest){
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			if(request.getValue() == 1) {
				Approval approval = timeSheetService.getByApprovalId(request.getId());
				approval.setApproval(1);
				timeSheetService.saveApproval(approval);
			}else {
				Timesheet getTimeSheet =  timeSheetService.getById(request.getId());
				getTimeSheet.setActive(0);
				timeSheetService.saveTimeSheet(getTimeSheet);
			}
			List<Report> approvalReport = timeSheetService.updateApproval(request.getOrgId(),request.getUserId());
			response.setReport(approvalReport);
			logger.info("Approval Success");
		}
		catch(Exception e){
			logger.error("Update approval failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/approvalList")
	public ReportResponse approvalList(@RequestBody TimeSheetListRequest request,HttpServletRequest servletRequest){
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<Report> approvalReport = timeSheetService.updateApproval(request.getOrgId(),request.getUserId());
			response.setReport(approvalReport);
			
			logger.info("ApprovalList Success");
		}
		catch(Exception e){
			logger.error("ApprovalList failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getApprovalList")
	public ReportResponse getApprovalList(@RequestBody TimeSheetListRequest request,HttpServletRequest servletRequest){
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<Report> getApprovval = timeSheetService.getApprovalList(request.getOrgId(),request.getUserId());
			response.setReport(getApprovval);
			
			logger.info("ApprovalList Success");
		}
		catch(Exception e){
			logger.error("ApprovalList failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/secured/approvalPagination")
	public ApprovalPaginationResponse approvalPagination(@RequestBody ApprovalPaginationRequest request,HttpServletRequest servletRequest){
		ApprovalPaginationResponse response = new ApprovalPaginationResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
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
			List<Report> approvalPage = timeSheetService.approvalPagination(from, to,request.getOrgId(),request.getUserId());
			response.setReport(approvalPage);
			logger.info("approval Pagination");
		}
		catch(Exception e){
			logger.error("Pagination failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	

	private void getActivity(HttpServletRequest request) {
		Cookie cookie = UuidProfile.getCookie(request, "userState");
		if(cookie != null) {
			User userToken  =  userMgmtService.getUserProfileToken(cookie.getValue());
			if(userToken != null){
				long roleId = userMgmtService.getUserRoleId(userToken.getId());
				UserSessionProfile userSessionProfile = new UserSessionProfile();
				userSessionProfile.setAdminId(roleId);
				userSessionProfile.setId(userToken.getId());
				userSessionProfile.setSecureToken(cookie.getValue());
				userSessionProfile.setOrgId(userToken.getOrgId());
				CacheService ehcs = new EhCacheServiceImpl();
				ehcs.putCache(cookie.getValue(), userSessionProfile);
			}
		}
	}
}

package com.projectLog.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projectLog.clib.model.SuccessIDResponse;
import com.projectLog.clib.util.DateTimeUtil;
import com.projectLog.clib.util.JSONUtil;
import com.projectLog.model.dbentity.ProjectUserMapping;
import com.projectLog.model.dbentity.Projects;
import com.projectLog.model.timesheet.HoursResponse;
import com.projectLog.model.timesheet.TimeSheetList;
import com.projectLog.model.timesheet.TimeSheetListRequest;
import com.projectLog.model.timesheet.TimeSheetListResponse;
import com.projectLog.model.timesheet.TimeSheetRequest;
import com.projectLog.model.timesheet.Timesheet;
import com.projectLog.service.ProjectService;
import com.projectLog.service.TimeSheetServcie;


@RestController
@RequestMapping("/timesheet")
@CrossOrigin( maxAge = 3600)
public class TimeSheetController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetController.class);
	
	@Autowired
	TimeSheetServcie timeSheetService;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(method = RequestMethod.POST, value="/updateTimeSheet")
	public SuccessIDResponse updateTimeSheet(@RequestBody TimeSheetRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			System.out.println(" timesheet request "+JSONUtil.toJson(request));
			Timesheet timesheet = new Timesheet();
			timesheet.setActivity(request.getTimeSheet().getActivity());
			timesheet.setCheckIn(request.getTimeSheet().getCheckIn());
			timesheet.setCheckOut(request.getTimeSheet().getCheckOut());
//			timesheet.setNoOfHrs(request);
			timesheet.setProjectId(request.getTimeSheet().getProjectId());
			timesheet.setUserId(request.getTimeSheet().getUserId());
			timesheet.setUpdatedDate(request.getTimeSheet().getUpdatedDate());
//			String hrs = DateTimeUtil.timeDifference(request.getTimeSheet().getCheckIn(), request.getTimeSheet().getCheckOut());
			DateFormat sdf = new SimpleDateFormat("HH:mm");
			Date date = sdf.parse(request.getTimeSheet().getNoOfHrs());
			timesheet.setNoOfHrs( sdf.format(date));
			 System.out.println("date time "+sdf.format(date));
			timeSheetService.saveTimeSheet(timesheet);
			
			String totalhrs = timeSheetService.getHrsById(timesheet.getProjectId(), 0);
			System.out.println("HRs "+totalhrs);
			String hrsPerUser = timeSheetService.getHrsById(timesheet.getProjectId(), timesheet.getUserId());
			Projects project = projectService.getProjectById(timesheet.getProjectId());
			project.setNoOfHrs(totalhrs);
			projectService.addProject(project);
			
			ProjectUserMapping projectMapping = projectService.getByProjectAndUserId(timesheet.getProjectId(), timesheet.getUserId());
			projectMapping.setHoursPerProject(hrsPerUser);
			projectService.saveMapping(projectMapping);
			
			logger.info("timesheet ");
		}
		catch(Exception e){
			logger.error("Time sheet fail",e);
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

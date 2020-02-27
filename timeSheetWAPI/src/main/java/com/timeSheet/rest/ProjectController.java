package com.timeSheet.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.clib.util.UuidProfile;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.project.ActivityPaginationRequest;
import com.timeSheet.model.project.ActivityPaginationResponse;
import com.timeSheet.model.project.AddMembersRequest;
import com.timeSheet.model.project.AddProjectRequest;
import com.timeSheet.model.project.GetCountResponse;
import com.timeSheet.model.project.ProjectDetail;
import com.timeSheet.model.project.ProjectDetailRequest;
import com.timeSheet.model.project.ProjectDetailResponse;
import com.timeSheet.model.project.ProjectListResponse;
import com.timeSheet.model.project.ProjectPaginationResponse;
import com.timeSheet.model.project.ProjectPaginatoinRequest;
import com.timeSheet.model.project.RemoveMemberRequest;
import com.timeSheet.model.project.SearchProjectRequest;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.timesheet.Project;
import com.timeSheet.model.timesheet.ReportRequest;
import com.timeSheet.model.timesheet.ReportResponse;
import com.timeSheet.model.timesheet.TimeSheetRequest;
import com.timeSheet.model.usermgmt.SearchUserDetailRequest;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserDetailResponse;
import com.timeSheet.model.usermgmt.UserHour;
import com.timeSheet.model.usermgmt.UserHourResponse;
import com.timeSheet.model.usermgmt.UserSessionProfile;
import com.timeSheet.service.ProjectService;
import com.timeSheet.service.UserMgmtService;

@RestController
@RequestMapping("/project")
@CrossOrigin( maxAge = 3600)
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserMgmtService userMgmtService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/addProject")
	public SuccessIDResponse addAndUpdateProject(@RequestBody AddProjectRequest request,HttpServletRequest servletRequest){
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getProject().getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
//			System.out.println("Request second"+JSONUtil.toJson(request));
			Projects project = new Projects();
			ProjectUserMapping projectUser = new ProjectUserMapping(); 
			project = projectService.getProjectById(request.getProject().getId());
			
			if(project == null){
				project = new Projects();
			}
			project.setProjectName(request.getProject().getProjectName());
			project.setStartDate(request.getProject().getStartDate());
			project.setEndDate(request.getProject().getEndDate());
			project.setProjectType(request.getProject().getProjectType());
			project.setDescription(request.getProject().getDescription());
			project.setUpdationDate(new Date());
			project.setOrgId(request.getProject().getOrgId());
			projectService.addProject(project);
			
			for(int i=0; i < request.getId().length; i++){
				//		System.out.println("Request second"+request.getId()[i]);
						String value = request.getId()[i];
						System.out.println("Request second kj"+value);
						int id = Integer.parseInt(value);
						projectUser = projectService.getByProjectAndUserId(project.getId(),id,request.getProject().getOrgId());
						if(projectUser == null) {
					projectUser = new ProjectUserMapping();
					projectUser.setProjectId(project.getId());
					projectUser.setUserId(id);
					projectService.saveMapping(projectUser);
					}
			}
			logger.info("project added");
		}
		catch(Exception e){
			logger.error("Project failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getProjectById")
	public ProjectDetailResponse getProjectDetail(@RequestBody ProjectDetailRequest request,HttpServletRequest servletRequest){
		ProjectDetailResponse response = new ProjectDetailResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			Projects project = projectService.getProjectById(request.getProjectId());
			response.setProject(project);
			logger.info("project added");
		}
		catch(Exception e){
			logger.error("Project failed",e);
			response.setSuccess(false);
			
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/secured/getProjectList")
	public ProjectListResponse getProjectList(@RequestBody ProjectDetailRequest request,HttpServletRequest servletRequest){
		ProjectListResponse response = new ProjectListResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<ProjectDetail> projects = projectService.getProjectList(request.getOrgId());
			response.setProjects(projects);
			logger.info("project added");
		}
		catch(Exception e){
			logger.error("Project failed",e);
			response.setSuccess(false);
			
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/addMembers")
	public SuccessIDResponse addMembers(@RequestBody AddMembersRequest request,HttpServletRequest servletRequest){
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			ProjectUserMapping projectUser = new ProjectUserMapping(); 
//			System.out.println("Request "+JSONUtil.toJson(request));
//			System.out.println("Request value"+request.getId());
			for(int i=0; i < request.getId().length; i++){
		//		System.out.println("Request second"+request.getId()[i]);
				String value = request.getId()[i];
				System.out.println("Request second kj"+value);
				int id = Integer.parseInt(value);
				projectUser = projectService.getByProjectAndUserId(request.getProjectId(),id,request.getOrgId());
				if(projectUser == null) {
				projectUser = new ProjectUserMapping();
				}
				projectUser.setProjectId(request.getProjectId());
				projectUser.setUserId(id);
				projectUser.setIsChecked("true");
				projectUser.setOrgId(request.getOrgId());
				projectService.saveMapping(projectUser);
			}
			logger.info("project mapping to user");
		}
		catch(Exception e){
			logger.error(" array ",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/projectPagination")
	public ProjectPaginationResponse projectPagination(@RequestBody ProjectPaginatoinRequest request,HttpServletRequest servletRequest){
		ProjectPaginationResponse response = new ProjectPaginationResponse();
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
			List<Projects> projects = projectService.projectPagination(from, to,request.getName(),request.getType(),request.getOrgId());
			response.setProjects(projects);
			logger.info("Project Pagination");
		}
		catch(Exception e){
			logger.error("Pagination failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/searchProject")
	public ProjectListResponse searchUserDetail(@RequestBody SearchProjectRequest request,HttpServletRequest servletRequest){
		ProjectListResponse response = new ProjectListResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<ProjectDetail> projectDetail = projectService.searchProjectDetail(request.getProjectDetail().getProjectName(),request.getProjectDetail().getProjectType(),request.getOrgId());
			response.setProjects(projectDetail);
			logger.info("search project detail");
		}
		catch(Exception e){
			logger.error("search project failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	@RequestMapping(method = RequestMethod.GET, value = "/getUserHourByProject/{id}")
	public UserHourResponse getUserHourByProject(@PathVariable int id){
		UserHourResponse response = new UserHourResponse();
		try{
			List<UserHour> userHour = projectService.getUserHour(id);
			response.setUserHour(userHour);
			logger.info("USer hour by id");
		}
		catch(Exception e){
			logger.error("User hour falied",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/projectListPagination")
	public ProjectListResponse projectListPagination(@RequestBody ProjectPaginatoinRequest request){
		ProjectListResponse response = new ProjectListResponse();
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
			List<ProjectDetail> projects = projectService.getProjectListPagination(
													request.getUserId(),request.getRoleId(),from, to);
			response.setProjects(projects);
			logger.info("Project Pagination");
		}
		catch(Exception e){
			logger.error("Pagination failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getCount")
	public GetCountResponse getCount(){
		GetCountResponse response = new GetCountResponse();
		try{
			long projectCount = projectService.getCount();
			long userCount = userMgmtService.getCount();
			response.setProjectCount(projectCount);
			response.setUserCount(userCount);
			logger.info("count");
		}
		catch(Exception e){
			logger.error("Count failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/secured/removeMember")
	public SuccessIDResponse removeMember(@RequestBody RemoveMemberRequest request,HttpServletRequest servletRequest){
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			ProjectUserMapping mapping = projectService.getByProjectAndUserId(request.getProjectId(), request.getUserId(),request.getOrgId());
			mapping.setIsChecked("false");
			projectService.saveMapping(mapping);
			logger.info("delete");
		}
		catch(Exception e){
			logger.error("delete failed",e);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getUserProject")
	public ProjectListResponse getUserByProject(@RequestBody RemoveMemberRequest request,HttpServletRequest servletRequest){
		ProjectListResponse response = new ProjectListResponse();
//		getActivity(servletRequest);
//			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
//				return response;
//			}
		try{
			List<ProjectDetail> userProjects = projectService.getUserProject(request.getUserId(),request.getOrgId());
			response.setProjects(userProjects);
			logger.info("USer hour by id");
		}
		catch(Exception e){
			logger.error("User hour falied",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getActivityById")
	public ReportResponse getActivityById(@RequestBody ReportRequest request,HttpServletRequest servletRequest){
		ReportResponse response = new ReportResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
			return response;
		}
		try{
			Activities activities = projectService.getActivityById(request.getActivityId());
			response.setActivities(activities);
			logger.info("get activity");
		}
		catch(Exception e){
			logger.error("get activityr falied",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/searchActivity")
	public ActivityPaginationResponse searchActivity(@RequestBody ActivityPaginationRequest request,HttpServletRequest servletRequest){
		ActivityPaginationResponse response = new ActivityPaginationResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<Activities> activities = projectService.searchActivity(request.getActivityDetail().getActivity(),request.getOrgId());
			response.setActivity(activities);
			logger.info("search activities detail");
		}
		catch(Exception e){
			logger.error("search activities failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/activityPagination")
	public ActivityPaginationResponse activityPagination(@RequestBody ActivityPaginationRequest request,HttpServletRequest servletRequest){
		ActivityPaginationResponse response = new ActivityPaginationResponse();
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
			List<Activities> activities = projectService.getActivityPagination(from, to,request.getActivity(),request.getOrgId());
			response.setActivity(activities);
			logger.info("activity Pagination");
		}
		catch(Exception e){
			logger.error("Pagination failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/addActivity")
	public SuccessIDResponse addActivity(@RequestBody ReportRequest request,HttpServletRequest servletRequest){
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
//			System.out.println("Request second"+JSONUtil.toJson(request));
			Activities activities= new  Activities();
			activities = projectService.getActivityById(request.getId());
			
			if(activities == null){
				activities = new Activities();
			}
			activities.setActivity(request.getActivities().getActivity());
			activities.setActivityDescription(request.getActivities().getActivityDescription());
			activities.setCreationDate(new Date());
			activities.setOrgId(request.getOrgId());
			projectService.saveActivities(activities);
			
			logger.info("activity added");
		}
		catch(Exception e){
			logger.error("activity failed",e);
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


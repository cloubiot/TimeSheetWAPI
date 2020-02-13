package com.timeSheet.rest;

import java.util.List;

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
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
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
import com.timeSheet.model.timesheet.TimeSheetRequest;
import com.timeSheet.model.usermgmt.UserHour;
import com.timeSheet.model.usermgmt.UserHourResponse;
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/addProject")
	public SuccessIDResponse addAndUpdateProject(@RequestBody AddProjectRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
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
			projectService.addProject(project);
			
			for(int i=0; i < request.getId().length; i++){
				//		System.out.println("Request second"+request.getId()[i]);
						String value = request.getId()[i];
						System.out.println("Request second kj"+value);
						int id = Integer.parseInt(value);
						projectUser = projectService.getByProjectAndUserId(project.getId(),id);
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/getProjectById")
	public ProjectDetailResponse getProjectDetail(@RequestBody ProjectDetailRequest request){
		ProjectDetailResponse response = new ProjectDetailResponse();
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

	@RequestMapping(method = RequestMethod.POST, value = "/getProjectList")
	public ProjectListResponse getProjectList(@RequestBody ProjectDetailRequest request){
		ProjectListResponse response = new ProjectListResponse();
		try{
			List<ProjectDetail> projects = projectService.getProjectList(request.getUserId(),request.getRoleId());
			response.setProjects(projects);
			logger.info("project added");
		}
		catch(Exception e){
			logger.error("Project failed",e);
			response.setSuccess(false);
			
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addMembers")
	public SuccessIDResponse addMembers(@RequestBody AddMembersRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			ProjectUserMapping projectUser = new ProjectUserMapping(); 
//			System.out.println("Request "+JSONUtil.toJson(request));
//			System.out.println("Request value"+request.getId());
			for(int i=0; i < request.getId().length; i++){
		//		System.out.println("Request second"+request.getId()[i]);
				String value = request.getId()[i];
				System.out.println("Request second kj"+value);
				int id = Integer.parseInt(value);
				projectUser = projectService.getByProjectAndUserId(request.getProjectId(),id);
				if(projectUser == null) {
				projectUser = new ProjectUserMapping();
				}
				projectUser.setProjectId(request.getProjectId());
				projectUser.setUserId(id);
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/projectPagination")
	public ProjectPaginationResponse projectPagination(@RequestBody ProjectPaginatoinRequest request){
		ProjectPaginationResponse response = new ProjectPaginationResponse();
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
			List<Projects> projects = projectService.projectPagination(from, to);
			response.setProjects(projects);
			logger.info("Project Pagination");
		}
		catch(Exception e){
			logger.error("Pagination failed",e);
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
	
	@RequestMapping(method = RequestMethod.POST, value ="/removeMember")
	public SuccessIDResponse removeMember(@RequestBody RemoveMemberRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			ProjectUserMapping mapping = projectService.getByProjectAndUserId(request.getProjectId(), request.getUserId());
			mapping.setIsChecked("false");
			logger.info("delete");
		}
		catch(Exception e){
			logger.error("delete failed",e);
		}
		return response;
	}
}


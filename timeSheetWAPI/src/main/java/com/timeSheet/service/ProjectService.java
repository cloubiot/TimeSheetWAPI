package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.ActivityRepository;
import com.timeSheet.dao.ProejctUserMappingRepository;
import com.timeSheet.dao.ProjectQuery;
import com.timeSheet.dao.ProjectRepository;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.project.ProjectDetail;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.usermgmt.UserHour;

@Service
@Transactional
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProejctUserMappingRepository mappingRepository;
	
	@Autowired
	ProjectQuery projectQuery;
	
	@Autowired
	ActivityRepository activityRepository;
	
	public Projects addProject(Projects project){
		return this.projectRepository.save(project);
	}
	
	public Projects getProjectById(int id){
		return this.projectRepository.findById(id);
	}
	
	public List<ProjectDetail> getProjectList(int orgId){
		return this.projectQuery.getProjectListByUser(orgId);
	}
	
	public ProjectUserMapping getByProjectAndUserId(int projectId, int userId,int orgId){
		return this.mappingRepository.findByProjectIdAndUserIdAndOrgId(projectId, userId,orgId);
	}
	public ProjectUserMapping saveMapping(ProjectUserMapping mapping){
		return this.mappingRepository.save(mapping);
	}
	public List<Projects> projectPagination(int from,int to,String name,String type,int orgId){
		return this.projectQuery.projectPagination(from, to,name,type,orgId);
	}
	public List<UserHour> getUserHour(int projectId){
		return this.projectQuery.getUserHour(projectId);
	}
	public List<ProjectDetail> getProjectListPagination(int userId,int roleId,int from, int to){
		return this.projectQuery.getProjectListPagination(userId,roleId, from, to);
	}
	public List<ProjectDetail> searchProjectDetail(String name,String type,int orgId){
		return this.projectQuery.searchProjectDetail(name,type,orgId);
	}
	public List<Activities> searchActivity(String name,int orgId){
		return this.projectQuery.searchActivity(name,orgId);
	}
	public List<ProjectDetail> getUserProject(int userId,int orgId){
		return this.projectQuery.getUserProject(userId,orgId);
	}
	public Long getCount(){
		return this.projectRepository.count();
	}
	public void removeMember(int projectId, int userId){
		this.projectQuery.removeMember(projectId, userId);
	}
	public Activities getActivityById(int activityId){
		return this.activityRepository.findById(activityId);
	}
	
	public List<Activities> getActivityPagination(int  from,int to,String activity){
		return this.projectQuery.getActivityPagination(from,to,activity);
	}
	
	public Activities saveActivities(Activities activities) {
		return this.activityRepository.save(activities);
	}
	
}

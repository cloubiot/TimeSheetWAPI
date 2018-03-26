package com.projectLog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectLog.dao.ProejctUserMappingRepository;
import com.projectLog.dao.ProjectQuery;
import com.projectLog.dao.ProjectRepository;
import com.projectLog.model.dbentity.ProjectUserMapping;
import com.projectLog.model.dbentity.Projects;
import com.projectLog.model.project.ProjectDetail;
import com.projectLog.model.usermgmt.UserHour;

@Service
@Transactional
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProejctUserMappingRepository mappingRepository;
	@Autowired
	ProjectQuery projectQuery;
	
	public Projects addProject(Projects project){
		return this.projectRepository.save(project);
	}
	
	public Projects getProjectById(int id){
		return this.projectRepository.findById(id);
	}
	
	public List<ProjectDetail> getProjectList(int userId,int roleId){
		return this.projectQuery.getProjectListByUser(userId,roleId);
	}
	
	public ProjectUserMapping getByProjectAndUserId(int projectId, int userId){
		return this.mappingRepository.findByProjectIdAndUserId(projectId, userId);
	}
	public ProjectUserMapping saveMapping(ProjectUserMapping mapping){
		return this.mappingRepository.save(mapping);
	}
	public List<Projects> projectPagination(int from,int to){
		return this.projectQuery.projectPagination(from, to);
	}
	public List<UserHour> getUserHour(int projectId){
		return this.projectQuery.getUserHour(projectId);
	}
	public List<ProjectDetail> getProjectListPagination(int userId,int roleId,int from, int to){
		return this.projectQuery.getProjectListPagination(userId,roleId, from, to);
	}
	
	public Long getCount(){
		return this.projectRepository.count();
	}
}

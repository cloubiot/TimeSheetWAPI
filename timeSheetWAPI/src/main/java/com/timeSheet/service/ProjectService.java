package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.ProejctUserMappingRepository;
import com.timeSheet.dao.ProjectQuery;
import com.timeSheet.dao.ProjectRepository;
import com.timeSheet.model.dbentity.ProjectUserMapping;
import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.project.ProjectDetail;
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
	public void removeMember(int projectId, int userId){
		this.projectQuery.removeMember(projectId, userId);
	}
	
}

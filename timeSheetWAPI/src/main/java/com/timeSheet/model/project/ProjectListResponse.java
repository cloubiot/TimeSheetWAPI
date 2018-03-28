package com.timeSheet.model.project;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Projects;

public class ProjectListResponse extends BaseResponse{

	List<ProjectDetail> projects;

	public List<ProjectDetail> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDetail> projects) {
		this.projects = projects;
	}
	
	
}

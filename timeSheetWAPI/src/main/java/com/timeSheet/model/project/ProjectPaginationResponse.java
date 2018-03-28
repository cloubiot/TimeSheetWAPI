package com.timeSheet.model.project;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Projects;

public class ProjectPaginationResponse extends BaseResponse{

	List<Projects> projects;

	public List<Projects> getProjects() {
		return projects;
	}

	public void setProjects(List<Projects> projects) {
		this.projects = projects;
	}
	
	
}

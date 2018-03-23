package com.projectLog.model.project;

import java.util.List;

import com.projectLog.clib.model.BaseResponse;
import com.projectLog.model.dbentity.Projects;

public class ProjectPaginationResponse extends BaseResponse{

	List<Projects> projects;

	public List<Projects> getProjects() {
		return projects;
	}

	public void setProjects(List<Projects> projects) {
		this.projects = projects;
	}
	
	
}

package com.timeSheet.model.project;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Projects;

public class ProjectDetailResponse extends BaseResponse{

	Projects project;

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}
	
}

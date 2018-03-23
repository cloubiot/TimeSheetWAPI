package com.projectLog.model.project;

import com.projectLog.clib.model.BaseResponse;
import com.projectLog.model.dbentity.Projects;

public class ProjectDetailResponse extends BaseResponse{

	Projects project;

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}
	
}

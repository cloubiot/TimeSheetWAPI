package com.timeSheet.model.project;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.Projects;

public class ProjectDetailResponse extends BaseResponse{

	Projects project;
	List<ProjectDetail> projectDetail;

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public List<ProjectDetail> getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(List<ProjectDetail> projectDetail) {
		this.projectDetail = projectDetail;
	}
	
	
}

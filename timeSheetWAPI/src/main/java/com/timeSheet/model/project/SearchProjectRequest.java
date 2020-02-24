package com.timeSheet.model.project;

public class SearchProjectRequest {

	ProjectDetail projectDetail;
	int orgId;
	int userId;

	public ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetail projectDetai) {
		this.projectDetail = projectDetai;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}

package com.projectLog.model.project;

public class AddMembersRequest {
	
	int projectId;
	String[] id = {};

	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}
	
	
}

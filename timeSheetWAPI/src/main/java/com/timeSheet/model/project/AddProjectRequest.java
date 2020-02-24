package com.timeSheet.model.project;

import com.timeSheet.model.dbentity.Projects;

public class AddProjectRequest {
	
	Projects project;
	String id[] = {};
	int userId;
	
	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	

}

package com.timeSheet.model.project;

import com.timeSheet.model.dbentity.Projects;

public class AddProjectRequest {
	
	Projects project;
	String id[] = {};
	
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
	
	

}

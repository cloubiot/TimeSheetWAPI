package com.timeSheet.model.usermgmt;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class UserTicketResponse extends BaseResponse {
	
	
	List<UserTicketProject> userTicketProject;
	String projectName;
	
	
	
	
	
	public List<UserTicketProject> getUserTicketProject() {
		return userTicketProject;
	}
	public void setUserTicketProject(List<UserTicketProject> userTicketProject) {
		this.userTicketProject = userTicketProject;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	

}

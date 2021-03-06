package com.timeSheet.model.dbentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProjectUserMapping {

	@Id
	@GeneratedValue
	int id;
	int projectId;
	int userId;
	String hoursPerProject;
	String isChecked;
	int orgId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getHoursPerProject() {
		return hoursPerProject;
	}
	public void setHoursPerProject(String hoursPerProject) {
		this.hoursPerProject = hoursPerProject;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	
}

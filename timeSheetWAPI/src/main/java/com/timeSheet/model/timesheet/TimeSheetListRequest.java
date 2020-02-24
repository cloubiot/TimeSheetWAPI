package com.timeSheet.model.timesheet;

import java.sql.Date;

public class TimeSheetListRequest {

	int userId;
	int projectId;
	int roleId;
	int value;
    Date date_1;
    int orgId;
	
public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getDate_1() {
		return date_1;
	}

	public void setDate_1(Date date_1) {
		this.date_1 = date_1;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	
}

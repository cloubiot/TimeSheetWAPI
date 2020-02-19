package com.timeSheet.model.timesheet;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ReportRequest {
	
	@Id
	@GeneratedValue
	Date date1;
	Date date2;
	Date date;
    int userId;
    int id;
    String task;
    int projectId;
	int activityId;
	ReportList reportList;
    Activities activities;
	int orgId;
	
	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public ReportList getReportList() {
		return reportList;
	}

	public void setReportList(ReportList reportList) {
		this.reportList = reportList;
	}

	public Activities getActivities() {
		return activities;
	}

	public void setActivities(Activities activities) {
		this.activities = activities;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
      
	

}

package com.timeSheet.model.organization;

import java.util.Date;

public class OrganizationDetails {
    
	int id;
	String name;
	String site;
	String type;
	String logo;
	String address;
	int userCnt;
	int projectCnt;
	int activityCnt;
	Date createdDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getUserCnt() {
		return userCnt;
	}
	public void setUserCnt(int userCnt) {
		this.userCnt = userCnt;
	}
	public int getProjectCnt() {
		return projectCnt;
	}
	public void setProjectCnt(int projectCnt) {
		this.projectCnt = projectCnt;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getActivityCnt() {
		return activityCnt;
	}
	public void setActivityCnt(int activityCnt) {
		this.activityCnt = activityCnt;
	}
	
	
}

package com.timeSheet.model.ticket;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivityResponse {

	
	int id;
	int issueId;
	String description;
	String comments;
	int statusId;
	@JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
	Date createdDate;
	int lastModUser;
	@JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
	Date lastModDate;
	int roleId;
	boolean mailNotes;
	String email;
	String phoneNumber;
	String userName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getLastModUser() {
		return lastModUser;
	}
	public void setLastModUser(int lastModUser) {
		this.lastModUser = lastModUser;
	}
	public Date getLastModDate() {
		return lastModDate;
	}
	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean isMailNotes() {
		return mailNotes;
	}
	public void setMailNotes(boolean mailNotes) {
		this.mailNotes = mailNotes;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}

package com.timeSheet.model.dbentity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AssignedGroups {

	@Id
	@GeneratedValue
	int id;
	String name;
	int groupOwner;
	String groupEmail;
	String description;
	String active;
	int primaryMember;
	int secondaryMember;
	int orgId;
	
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
	public int getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(int groupOwner) {
		this.groupOwner = groupOwner;
	}
	public String getGroupEmail() {
		return groupEmail;
	}
	public void setGroupEmail(String groupEmail) {
		this.groupEmail = groupEmail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public int getPrimaryMember() {
		return primaryMember;
	}
	public void setPrimaryMember(int primaryMember) {
		this.primaryMember = primaryMember;
	}
	public int getSecondaryMember() {
		return secondaryMember;
	}
	public void setSecondaryMember(int secondaryMember) {
		this.secondaryMember = secondaryMember;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	
	
}

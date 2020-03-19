package com.timeSheet.model.group;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.AssignedGroups;


public class GroupsResponse extends BaseResponse{

	List<AssignedGroups> groups;

	public List<AssignedGroups> getGroups() {
		return groups;
	}

	public void setGroups(List<AssignedGroups> groups) {
		this.groups = groups;
	}
	
}

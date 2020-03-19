package com.timeSheet.model.group;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;


public class TotalGroupsResponse extends BaseResponse{

	List<TotalGroups> groups;

	public List<TotalGroups> getGroups() {
		return groups;
	}

	public void setGroups(List<TotalGroups> groups) {
		this.groups = groups;
	}
	
	
}

package com.timeSheet.model.group;

import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.dbentity.AssignedGroups;

public class AssignGroupResponse extends BaseResponse{

	AssignedGroups group;

	public AssignedGroups getGroup() {
		return group;
	}

	public void setGroup(AssignedGroups group) {
		this.group = group;
	}
	
	
}

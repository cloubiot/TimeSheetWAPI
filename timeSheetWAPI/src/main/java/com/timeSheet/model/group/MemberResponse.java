package com.timeSheet.model.group;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;


public class MemberResponse extends BaseResponse{
	
	List<MemberListInGroup> memberList;

	public List<MemberListInGroup> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberListInGroup> memberList) {
		this.memberList = memberList;
	}
	
}

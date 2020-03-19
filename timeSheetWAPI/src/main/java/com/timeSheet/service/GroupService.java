package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.AssignedGroupRepository;
import com.timeSheet.dao.GroupMappingRepository;
import com.timeSheet.dao.GroupQuery;
import com.timeSheet.model.dbentity.AssignedGroups;
import com.timeSheet.model.dbentity.GroupMapping;
import com.timeSheet.model.group.MemberListInGroup;
import com.timeSheet.model.group.TotalGroups;

@Service
@Transactional
public class GroupService {

	@Autowired
	AssignedGroupRepository assignedRepository;
	
	@Autowired
	GroupMappingRepository groupMappingRepository;
	
	@Autowired
	GroupQuery groupQuery;
	
	public AssignedGroups saveGroup(AssignedGroups group){
		return this.assignedRepository.save(group);
	}	
	public GroupMapping saveGroupMapping(GroupMapping groupMapping){
		return this.groupMappingRepository.save(groupMapping);
	}	
	public GroupMapping getByUserId(int userId){
		return this.groupMappingRepository.findByUserId(userId);
	}

	public List<AssignedGroups> getActiveGroups(){
		return this.groupQuery.getActiveGroups();
	}	
	
	public AssignedGroups getById(int id){
		return this.assignedRepository.findById(id);
	}
	public List<AssignedGroups> getAll(){
		 return this.assignedRepository.findAll();
	}
	public List<TotalGroups> getAllGroups(int orgId){
		return this.groupQuery.getAllGroups(orgId);
	}
	public List<MemberListInGroup> getMemberByGroupId(int id){
		return this.groupQuery.getMemberById(id);
	}
	public List<MemberListInGroup> getPrimaryMembers(int groupId,int value){
		return this.groupQuery.primaryMembers(groupId, value);
	}

	public List<MemberListInGroup> getSecondaryMembers(int groupId,int leadValue,int primaryValue){
		return this.groupQuery.secondaryMembers(groupId, leadValue, primaryValue);
	}
	public boolean removeMember(int id){
		return this.groupQuery.removeMember(id);
	}
	
}

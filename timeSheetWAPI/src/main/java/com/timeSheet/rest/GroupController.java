package com.timeSheet.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timeSheet.clib.model.SuccessIDResponse;
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.AssignedGroups;
import com.timeSheet.model.group.AddGroupRequest;
import com.timeSheet.model.group.AssignGroupResponse;
import com.timeSheet.model.group.GroupsResponse;
import com.timeSheet.model.group.MemberListInGroup;
import com.timeSheet.model.group.MemberResponse;
import com.timeSheet.model.group.PrimaryMemberRequest;
import com.timeSheet.model.group.SecondaryMemberRequest;
import com.timeSheet.model.group.TotalGroups;
import com.timeSheet.model.group.TotalGroupsResponse;
import com.timeSheet.service.GroupService;

@RestController
@RequestMapping("/group")
@CrossOrigin( maxAge = 3600)

public class GroupController {

	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	GroupService groupService;
	
	
	@RequestMapping(method = RequestMethod.POST, value="/addgroup")
	public SuccessIDResponse addGroup(@RequestBody AddGroupRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			AssignedGroups group = new AssignedGroups();
			if(request.getAssignedGroup().getId() == 0){
			group.setName(request.getAssignedGroup().getName());
			group.setGroupOwner(request.getAssignedGroup().getGroupOwner());
			group.setGroupEmail(request.getAssignedGroup().getGroupEmail());
			group.setDescription(request.getAssignedGroup().getDescription());
			group.setPrimaryMember(request.getAssignedGroup().getPrimaryMember());
			group.setSecondaryMember(request.getAssignedGroup().getSecondaryMember());
			group.setOrgId(request.getAssignedGroup().getOrgId());
			group.setActive("true");
			groupService.saveGroup(group);
			response.setSuccess(true);
			logger.info("NewGroup");
			}
			else{
				group = groupService.getById(request.getAssignedGroup().getId());
				group.setName(request.getAssignedGroup().getName());
				group.setGroupOwner(request.getAssignedGroup().getGroupOwner());
				group.setGroupEmail(request.getAssignedGroup().getGroupEmail());
				group.setDescription(request.getAssignedGroup().getDescription());
				group.setPrimaryMember(request.getAssignedGroup().getPrimaryMember());;
				group.setSecondaryMember(request.getAssignedGroup().getSecondaryMember());
				group.setActive(request.getAssignedGroup().getActive());
				groupService.saveGroup(group);
				response.setSuccess(true);
				logger.info("While edit");
				
			}
		}
		catch(Exception e){
			logger.error("Group add or edit failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getGroups/{orgId}")
	public TotalGroupsResponse getGroups(@PathVariable int orgId){
		TotalGroupsResponse response = new TotalGroupsResponse();
		try{
			List<TotalGroups> groups = groupService.getAllGroups(orgId);
			response.setGroups(groups);
			System.out.println(JSONUtil.toJson(groups));
			logger.info("Get Groups");
		}
		catch(Exception e){
			logger.error("GEt Groups Failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getGroup/{id}")
	public AssignGroupResponse getGroupById(@PathVariable int id){
		AssignGroupResponse response = new AssignGroupResponse();
		try{
			AssignedGroups group = groupService.getById(id);
			response.setGroup(group);
			logger.info("get by id");
		}
		catch(Exception e){
			logger.error("Group Failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMembersById/{id}")
	public MemberResponse getMemberByGroupId(@PathVariable int id){
		MemberResponse response = new MemberResponse();
		try{
			List<MemberListInGroup> group = groupService.getMemberByGroupId(id);
			response.setMemberList(group);
			System.out.println(JSONUtil.toJson(group));
			logger.info("Get By GroupId");
		}
		catch(Exception e){
			logger.error("Get By Group Failed",e);
			response.setSuccess(false);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/primaryMember")
	public MemberResponse primaryMember(@RequestBody PrimaryMemberRequest request){
		MemberResponse response = new MemberResponse();
		try{
			List<MemberListInGroup> group = groupService.getPrimaryMembers(request.getGroupId(), request.getValue());
			response.setMemberList(group);
			logger.info("primary memebers");
		}
		catch(Exception e){
			logger.error("primary members failed",e);
			response.setSuccess(false);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/secondaryMember")
	public MemberResponse primaryMember(@RequestBody SecondaryMemberRequest request){
		MemberResponse response = new MemberResponse();
		try{
			List<MemberListInGroup> group = groupService.getSecondaryMembers(request.getGroupId(), request.getLeadValue(),request.getPrimaryValue());
			response.setMemberList(group);
			logger.info("secondary memebers");
		}
		catch(Exception e){
			logger.error("secondary members failed",e);
			response.setSuccess(false);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/removeMember/{id}")
	public SuccessIDResponse removeMember(@PathVariable int id){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			boolean isRemoved = groupService.removeMember(id);
			if(isRemoved == true){
				response.setSuccess(true);
			}
			else{
				response.setSuccess(false);
			}
		}
		catch(Exception e){
			logger.error("",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getActiveGroups")
	public GroupsResponse getActiveGroups(){
		GroupsResponse response = new GroupsResponse();
		try{
			List<AssignedGroups> groups = groupService.getActiveGroups();
			response.setGroups(groups);
			logger.info("Get Groups");
		}
		catch(Exception e){
			logger.error("GEt Groups Failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
}

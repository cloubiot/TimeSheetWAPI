package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.AssignedGroups;
import com.timeSheet.model.group.MemberListInGroup;
import com.timeSheet.model.group.TotalGroups;


@Service
@Transactional
public class GroupQuery {

	@Autowired
    JdbcTemplate jdbcTemplate;
	public List<AssignedGroups> getActiveGroups(){
		String query = "select * from assigned_groups where active='true'";
		List<AssignedGroups> groups = jdbcTemplate.query(query, new BeanPropertyRowMapper(AssignedGroups.class));
		return groups;
	}
	public List<TotalGroups> getAllGroups(int orgId){
		String query = "select assigned_groups.*,user.first_name from assigned_groups "
						+"inner join user on user.id = assigned_groups.group_owner where assigned_groups.org_id ="+orgId; 
		List<TotalGroups> groups = jdbcTemplate.query(query, new BeanPropertyRowMapper(TotalGroups.class));
		System.out.println("grooups    o"+JSONUtil.toJson(query));
		return groups;
	}

	public List<MemberListInGroup> getMemberById(int id){
		String query = "select group_mapping.group_Id,user.*,user_role_mapping.role_Id,user_role.desc from group_mapping "
						+" inner join user on user.id = group_mapping.user_id "
						+" inner join user_role_mapping on user_role_mapping.user_id = user.id"
						+" inner join user_role on user_role.id = user_role_mapping.role_Id where group_mapping.group_id ="+id;
		List<MemberListInGroup> group = jdbcTemplate.query(query, new BeanPropertyRowMapper(MemberListInGroup.class));
		return group;
	}
	public List<MemberListInGroup> primaryMembers(int groupId,int value){
		String query = "select group_mapping.group_Id,user.*,user_role_mapping.role_Id,user_role.desc from group_mapping "
				+" inner join user on user.id = group_mapping.user_id "
				+" inner join user_role_mapping on user_role_mapping.user_id = user.id"
				+" inner join user_role on user_role.id = user_role_mapping.role_Id where group_mapping.group_id ="+groupId+" and not user.id="+value;
		List<MemberListInGroup> group = jdbcTemplate.query(query, new BeanPropertyRowMapper(MemberListInGroup.class));
		return group;
	}	

	public List<MemberListInGroup> secondaryMembers(int groupId,int leadValue,int primaryValue){
		System.out.println("group : "+groupId+" lead : "+leadValue+" primary : "+primaryValue);
		String query = "select group_mapping.group_Id,user_A.*,user_B.*,user_role_mapping.role_Id,user_role.desc from group_mapping "
				+" inner join user as user_A on user_A.id = group_mapping.user_id "
				+" inner join user as user_B on user_B.id = group_mapping.user_id "
				+" inner join user_role_mapping on user_role_mapping.user_id = user_A.id"
				+" inner join user_role on user_role.id = user_role_mapping.role_Id where group_mapping.group_id ="+groupId+" and not user_A.id="+leadValue+" and not user_B.id="+primaryValue;
		List<MemberListInGroup> group = jdbcTemplate.query(query, new BeanPropertyRowMapper(MemberListInGroup.class));
		return group;
	}

	public boolean removeMember(int id){
		String query = "update group_mapping set GROUP_ID = 0 where user_id = "+id;
		jdbcTemplate.execute(query);
		return true;
	}
	
}

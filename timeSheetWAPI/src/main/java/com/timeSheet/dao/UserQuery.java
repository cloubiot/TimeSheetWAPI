package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.SecureData;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.timesheet.Report;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserTicketProject;
import com.timeSheet.model.usermgmt.UserWithRole;
import com.timeSheet.model.dbentity.GroupMapping;
import com.timeSheet.model.dbentity.Organization;


@Service
@Transactional
public class UserQuery {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<User> login(String email,String password){
		String query = "select user.id,user_name,first_name,last_name,email,phone_number,profile_image_url,active,creation_date,updation_date,org_id,org.name as taxRate,secure_token from user "
				        +"left join organization as org on org.id = user.org_id "
						+"where email='"+email+"' and password='"+password+"'";
		List<User> user = jdbcTemplate.query(query, new BeanPropertyRowMapper(User.class));
		return user;
	}
	public Organization orgLogin(String email,String password){
		String query = "select user.id,org.logo,user_name,first_name,last_name,email,phone_number,profile_image_url,active,creation_date,updation_date,org_id,org.name as taxRate,secure_token from user "
				        +"left join organization as org on org.id = user.org_id "
						+"where email='"+email+"' and password='"+password+"'";
		Organization orgUser = (Organization) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(Organization.class));
		return orgUser;
	}
	public User findByIdAndPassword(int id,String password) {
		String query = "select * FROM cloubiotproject.user where id="+id+" and PASSWORD='"+password+"'";
		User findByIdAndPassword = (User) jdbcTemplate.query(query, new BeanPropertyRowMapper(User.class));
		return findByIdAndPassword;
	}
	public User emailIdExists(String email,int userId){
		String query = " select id,email from user ";
		if(userId == 0)
			query+=" where email ='"+email+"'";
		if(userId != 0)
			query+=" where email ='"+email+"' and id="+userId;
		
		User user = (User) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(User.class));
		return user;
	}
	public int getUserRoleId(long userId) {
		int roleId = 2;
		String qry = "select role_id from user_role_mapping where user_id=?";
		try {
			//roleId = (Integer) jdbcTemplate.queryForObject(qry, new BeanPropertyRowMapper(Integer.class));
			roleId = jdbcTemplate.queryForObject(
					qry, new Object[] { userId }, Integer.class);

		} catch (EmptyResultDataAccessException e) {
			roleId=2;
		}catch (Exception e) {
			roleId=2;
		}
		return roleId;
	}
	
	public List<UserWithRole> getUserList(int projectId,int orgId){
		String query = "select user_role_mapping.ROLE_ID,case when PROJECT_ID = "+projectId+" and project_user_mapping.IS_CHECKED='true' then 'true' end as is_checked,user.* from user " 
						+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id "
						+"left join project_user_mapping on project_user_mapping.USER_ID = user.id and PROJECT_ID ="+projectId+" where ACTIVE != 'false' and user.org_id ="+orgId;
//		System.out.println("@@@@@@"+query);
		List<UserWithRole> users = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserWithRole.class));
		return users;
	}
	
	
	
	public List<UserWithRole> searchUser(String name,int roleId){
		String query = "select user_role_mapping.ROLE_ID, user.* from user " 
						+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id " 
						+"where email like '%"+name+"%' and user_role_mapping.role_id="+roleId;
//		System.out.println("@@@@@@"+query);
		List<UserWithRole> users = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserWithRole.class));
		return users;
	}
	
	public List<UserDetail> getUserDetail(int id){
		String query = "select user_role.desc,user_role_mapping.ROLE_ID,group_mapping.GROUP_ID, user.* from user " 
				+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id "
				+"inner join user_role on user_role.ROLE = user_role_mapping.ROLE_ID "
				+"inner join group_mapping on user.id = group_mapping.USER_ID "
				+"where user.ORG_ID ="+id;
		List<UserDetail> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserDetail.class));
		return userDetail;
	}
	
	
	
	public List<UserDetail> getPaginationForUser(int from, int to,String email,String name,int orgId){
		if(name == null)
			name="";
		if(email== null)
			email="";
		String query = "select user_role.desc,user_role_mapping.ROLE_ID, user.* from user " 
				+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id "
				+"left join user_role on user_role.ROLE = user_role_mapping.ROLE_ID "
				+"where FIRST_NAME like '%"+name+"%' and EMAIL like '%"+email+"%' and user.org_id="+orgId;
		query+=" limit "+from+" ,"+to;
		List<UserDetail> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserDetail.class));
		return userDetail;
	}
	
	public List<UserDetail> searchUserDetail(String name,String mailId,String phoneNumber,String role,String active,int orgId){
		if(name == null)
			name="";
		if(mailId== null)
			mailId="";
		if(phoneNumber == null)
			phoneNumber = "";
		if(role == null)
			role="";
		if(active == null)
			active="";
		String query = "select user_role.desc,user_role_mapping.ROLE_ID, user.* from user " 
				+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id "
				+"left join user_role on user_role.ROLE = user_role_mapping.ROLE_ID "
				+"where user.first_name like '%"+name+"%'"
				+" and user.email like '%"+mailId+"%' and user.org_id ="+orgId;
//		System.out.println("#####"+query);
		List<UserDetail> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserDetail.class));
		return userDetail;
	}
	
	public List<UserDetail> getActiveUser(String value){
		String query = "select user_role.desc,user_role_mapping.ROLE_ID, user.* from user " 
				+"inner join user_role_mapping on user_role_mapping.USER_ID = user.id "
				+"inner join user_role on user_role.ROLE = user_role_mapping.ROLE_ID where ";
		if(value.equals("true"))
			query+=" user.active = 1";
		else
			query+=" user.active = 0";
		List<UserDetail> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserDetail.class));
		return userDetail;
	}
	
	public List<UserDetail> getUserByProjectId(int projectId){
		String query = "select user.id,user.user_Name,user.email,user.phone_number from user "+
							"inner join project_user_mapping on project_user_mapping.user_id = user.id "+
							" where project_user_mapping.is_checked = 'true' and project_user_mapping.project_id = "+projectId;
		List<UserDetail> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserDetail.class));
		return userDetail;
	}

	public List<UserTicketProject> getUserTicket(int id){
		String query = "select user.*,project_user_mapping.PROJECT_ID,projects.PROJECT_NAME from user  "
				+ "inner join project_user_mapping on project_user_mapping.USER_ID = user.id inner join projects on projects.ID = project_user_mapping.PROJECT_ID"
				+ " where project_user_mapping.IS_CHECKED ='true' and user.id ="+id;
		List<UserTicketProject> userDetail =  jdbcTemplate.query(query, new BeanPropertyRowMapper(UserTicketProject.class));
		return userDetail;
	}
	public List<User> findSecureToken(String token){
		String query = "SELECT *,user.id as id,org.NAME as taxRate FROM user " 
				       +"inner join organization as org on org.id = user.org_id "
				       +"where secure_token='"+token+"'";
		List<User> userDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(User.class));
		return userDetail;
	}
	public Organization orgLogo(String token){
		String query = "select org.logo from user "
				        +"left join organization as org on org.id = user.org_id "
						+"where secure_token='"+token+"'";
		Organization orgUser = (Organization) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(Organization.class));
		return orgUser;
	}
}

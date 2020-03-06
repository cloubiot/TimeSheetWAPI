package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.model.dbentity.Projects;
import com.timeSheet.model.project.ProjectDetail;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.usermgmt.UserHour;

@Service
@Transactional
public class ProjectQuery {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<ProjectDetail> getProjectListByUser(int orgId){
		String query = null;
			query =" select * from projects where org_id="+orgId;
//		else{
//			query = "select projects.*,project_user_mapping.hours_per_project from projects inner join project_user_mapping on project_user_mapping.project_id = projects.id"
//					+ " where project_user_mapping.user_id ="+userId;
//		}
		List<ProjectDetail> projects = jdbcTemplate.query(query, new BeanPropertyRowMapper(ProjectDetail.class));
		return projects;
	}
	public List<Projects> projectPagination(int from,int to,String name,String type,int orgId){
		if(name == null)
			name="";
		if(type== null)
			type="";
		String query ="select * from projects "
				+" where project_name like '%"+name+"%'"
				+" and project_type like '%"+type+"%' and org_id ="+orgId+" limit "+from+" ,"+to;
		List<Projects> projects = jdbcTemplate.query(query, new BeanPropertyRowMapper(Projects.class));
		return projects;
	}
	
	public List<UserHour> getUserHour(int projectId){
		String query = "select project_user_mapping.hours_per_project,user.USER_NAME from project_user_mapping "
							+" inner join user on user.id = project_user_mapping.USER_ID "
							+" where project_user_mapping.PROJECT_ID ="+projectId;
		 List<UserHour> userHour = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserHour.class));
		return userHour;
	}
	
	public List<ProjectDetail> getProjectListPagination(int userId,int roleId, int from, int to){
		String query = null;
		if(roleId == 1){
			query =" select * from projects";
		 
		}
		else{
			query = "select projects.*,project_user_mapping.hours_per_project from projects inner join project_user_mapping on project_user_mapping.project_id = projects.id"
					+ " where project_user_mapping.user_id ="+userId;
		}
			query+=" limit "+from+" , "+to;
		List<ProjectDetail> projects = jdbcTemplate.query(query, new BeanPropertyRowMapper(ProjectDetail.class));
		return projects;
	}
	public List<ProjectDetail> searchProjectDetail(String name,String type,int orgId){
		if(name == null)
			name="";
		if(type== null)
			type="";
		String query = "select * from projects"
				+" where project_name like '%"+name+"%'"
				+" and project_type like '%"+type+"%' and org_id="+orgId;
		List<ProjectDetail> searchProject = jdbcTemplate.query(query, new BeanPropertyRowMapper(ProjectDetail.class));
		return searchProject;
	}
	public List<Activities> searchActivity(String name,int orgId){
		if(name == null)
			name="";
		String query = "select * from activities"
				+" where activity like '%"+name+"%'"
				+" and org_id="+orgId;
		List<Activities> activities = jdbcTemplate.query(query, new BeanPropertyRowMapper(Activities.class));
		return activities;
	}
	
	public void removeMember(int projectId, int userId){
		String query = "Delete from  project_user_mapping where user_id="+userId+" and project_id="+projectId;
		jdbcTemplate.execute(query);
		
	}
	
	public List<ProjectDetail> getUserProject(int userId,int orgId){
		String query = "select project_id as id,project_name,user_id from "  
				+"((project_user_mapping inner join projects on projects.id = project_user_mapping.project_id) "
				+"inner join user on user.id = project_user_mapping.user_id) where user_id ="+userId+" and project_user_mapping.org_id ="+orgId+" and is_checked = 'true'";
		List<ProjectDetail> userProjects = jdbcTemplate.query(query, new BeanPropertyRowMapper(ProjectDetail.class));
		return userProjects;
	}
	
	public List<Activities> getActivityPagination(int from,int to,String activity,int orgId){
		
		if(activity == null) {
			activity = "";
		}
		String query = "select * from activities where activity like '%"+activity+"%' and org_id="+orgId+" limit "+from+","+to;
		List<Activities> activities = jdbcTemplate.query(query, new BeanPropertyRowMapper(Activities.class));
		return activities;
	}
}

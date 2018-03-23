package com.projectLog.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectLog.model.dbentity.Projects;
import com.projectLog.model.project.ProjectDetail;
import com.projectLog.model.usermgmt.UserHour;

@Service
@Transactional
public class ProjectQuery {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<ProjectDetail> getProjectListByUser(int userId,int roleId){
		String query = null;
		if(roleId == 1){
			query =" select * from projects";
		 
		}
		else{
			query = "select projects.*,project_user_mapping.hours_per_project from projects inner join project_user_mapping on project_user_mapping.project_id = projects.id"
					+ " where project_user_mapping.user_id ="+userId;
		}
		List<ProjectDetail> projects = jdbcTemplate.query(query, new BeanPropertyRowMapper(ProjectDetail.class));
		return projects;
	}
	public List<Projects> projectPagination(int from,int to){
		String query ="select * from projects limit "+from+" ,"+to;
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
}

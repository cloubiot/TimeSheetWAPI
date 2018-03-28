package com.projectLog.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectLog.model.timesheet.HoursResponse;
import com.projectLog.model.timesheet.TimeSheetList;
import com.projectLog.model.usermgmt.UserWithRole;

@Service
@Transactional
public class TimeSheetQuery {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<TimeSheetList> getTimeSheetByUserId(int userId,int projectId, int roleId){
		
		String query = " select timesheet.*,user.user_name from timesheet "+
							" inner join user on user.id = timesheet.user_id where timesheet.project_id="+projectId;
		if(roleId > 1)	
			query+=" and timesheet.user_id = "+userId; 
		query+=" order by id DESC";
		
		List<TimeSheetList> timeSheet = jdbcTemplate.query(query, new BeanPropertyRowMapper(TimeSheetList.class));
		return timeSheet;
	}
	
	public String getHrsById(int projectId,int userId){
		String query =" select SEC_TO_TIME( SUM(TIME_TO_SEC(NO_OF_HRS))) as TOTAL_HRS from timesheet "
						+" where project_id ="+projectId;
		if(userId != 0)
			query+=" and user_id="+userId;
		String totalHrs =  jdbcTemplate.queryForObject(query,String.class);
		return totalHrs;
	}
	
	public List<TimeSheetList> getTimeSheetPagination(int userId,int projectId, int roleId, int from, int to){
		
		String query = " select timesheet.*,user.user_name from timesheet "+
							" inner join user on user.id = timesheet.user_id where timesheet.project_id="+projectId;
		if(roleId > 1)	
			query+=" and timesheet.user_id = "+userId; 
		query+=" order by id DESC limit "+from+" , "+to;
		
		List<TimeSheetList> timeSheet = jdbcTemplate.query(query, new BeanPropertyRowMapper(TimeSheetList.class));
		return timeSheet;
	}
}

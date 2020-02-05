package com.timeSheet.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.timesheet.Activity;
import com.timeSheet.model.timesheet.HoursResponse;
import com.timeSheet.model.timesheet.Project;
import com.timeSheet.model.timesheet.Report;
import com.timeSheet.model.timesheet.ReportList;
import com.timeSheet.model.timesheet.TimeSheetList;
import com.timeSheet.model.timesheet.Timesheet;
import com.timeSheet.model.usermgmt.UserWithRole;

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
		String query =" select SUM(NO_OF_HRS) as TOTAL_HRS from timesheet "
						+" where project_id ="+projectId;
		if(userId != 0)
			query+=" and user_id="+userId;
		String totalHrs =  jdbcTemplate.queryForObject(query,String.class);
		return totalHrs;
	}
	public List<Report> getReport(int userId){
		String query = "select USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK from ((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) inner join activities on activities.ID = timesheet.ACTIVITY_ID) where USER_ID ="+userId+" and date between curdate() - interval 7 day and curdate()";
//		System.out.println("&&&&&"+query);	
		List<Report> getReport = jdbcTemplate.query(query, new BeanPropertyRowMapper(Report.class));
		return getReport;
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
	
	public List<Project> getProject(){
		String query = "SELECT * FROM cloubiotproject.projects";
		List<Project> getProject = jdbcTemplate.query(query, new BeanPropertyRowMapper(Project.class));
		return getProject;
	
	}
	
	public List<Activity> getActivity(){
		String query = "SELECT * FROM cloubiotproject.activities";
		List<Activity> getActivity = jdbcTemplate.query(query, new BeanPropertyRowMapper(Activity.class));
		return getActivity;
	
	}
	
	public List<Timesheet> findUpdateHrs(String project,String activity,Date date,int userId){
		String query = "select * from timesheet where date='"+date+"' and project_name='"+project+"' and activity like '%"+activity+"%' and user_id="+userId;
		List<Timesheet> findUpdateHrs =  jdbcTemplate.query(query, new BeanPropertyRowMapper(Timesheet.class));
		return findUpdateHrs;
	}
	
	public List<Timesheet> findActivity(String project,String activity,Date date,int userId){
		String query = "select * from timesheet where date='"+date+"' and project_name='"+project+"' and activity not like '%"+activity+"%' and user_id="+userId;
		List<Timesheet> findActivity = jdbcTemplate.query(query, new BeanPropertyRowMapper(Timesheet.class));
		return findActivity;
	}
	public List<ReportList> getReportlist(int id,Date date1,Date date2 ){
		String query = "select USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK  from ((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) inner join activities on activities.ID = timesheet.ACTIVITY_ID) where USER_ID = "+id+" and DATE between '"+date1+"' and '"+date2+"' ";
//		System.out.println("%%%%%"+query);
//		System.out.println("%%%%%"+date2);
		if(date2 == new Date(id)) {
			query+="select USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK from ((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) inner join activities on activities.ID = timesheet.ACTIVITY_ID) where USER_ID ="+id+"and DATE >='"+date1+"'";
			
		}
		
		List<ReportList> getReportlist = jdbcTemplate.query(query, new BeanPropertyRowMapper(ReportList.class));
		return getReportlist;
	}
	
}

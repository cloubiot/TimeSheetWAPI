package com.timeSheet.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.timesheet.Activities;
import com.timeSheet.model.timesheet.Approval;
import com.timeSheet.model.timesheet.HoursResponse;
import com.timeSheet.model.timesheet.Project;
import com.timeSheet.model.timesheet.Report;
import com.timeSheet.model.timesheet.ReportList;
import com.timeSheet.model.timesheet.Reportview;
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
		String query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,active "
				+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) inner join activities on activities.ID = timesheet.ACTIVITY_ID) "
				+ "inner join approval on approval.timesheet_id = timesheet.ID) "
				+ "where timesheet.USER_ID ="+userId+" and date  <= curdate() - INTERVAL DAYOFWEEK(curdate())-7 DAY and date > curdate() - INTERVAL DAYOFWEEK(curdate())-0 DAY order by date";
		List<Report> getReport = jdbcTemplate.query(query, new BeanPropertyRowMapper(Report.class));
		return getReport;
	}
	
	public List<Reportview> getReportview(int userId,String date){
		String query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,active "
				+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) "
				+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID) "
				+ "inner join approval on approval.timesheet_id = timesheet.ID) "
				+ "where timesheet.USER_ID ="+userId+" and date  <= '"+date+"' - INTERVAL DAYOFWEEK('"+date+"')-7 DAY and date > '"+date+"' - INTERVAL DAYOFWEEK('"+date+"')-0 DAY order by date";
		List<Reportview> getReportview = jdbcTemplate.query(query, new BeanPropertyRowMapper(Reportview.class));
		return getReportview;
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
	
	public List<Project> getProject(int orgId){
		String query = "SELECT * FROM projects where org_id="+orgId;
		List<Project> getProject = jdbcTemplate.query(query, new BeanPropertyRowMapper(Project.class));
		return getProject;
	
	}
	
	public List<Activities> getActivity(int orgId){
		String query = "SELECT * FROM activities where org_id="+orgId;
		List<Activities> getActivity = jdbcTemplate.query(query, new BeanPropertyRowMapper(Activities.class));
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
	public List<ReportList> getReportlist(String user,Date date1,Date date2,String project,String activity,int orgId){
		if(user.equalsIgnoreCase("0"))
		    user="";
        if(project.equalsIgnoreCase("0"))
            project = "";
        if(activity.equalsIgnoreCase("0"))
        	activity = "";
        String query = "select USER_ID,u.USER_NAME,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK " 
        		+"from timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID " 
        		+"inner join activities on activities.ID = timesheet.ACTIVITY_ID " 
        		+"inner join user as u on u.id = timesheet.USER_ID " 
        		+"where user_name like '%"+user+"' and project_name like '%"+project+"' and activity like '%"+activity+"' and Date between '"+date1+"' and '"+date2+"' and u.org_id="+orgId+" order by date ";
		
		List<ReportList> getReportlist = jdbcTemplate.query(query, new BeanPropertyRowMapper(ReportList.class));
		return getReportlist;
	}
	public List<Report> updateApproval(int from,int to,int orgId,int userId){
		String query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,approval.org_id "
				+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) "
				+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID)  "
				+ "inner join approval on approval.timesheet_id = timesheet.ID) ";
    if(userId == 0) {
		        query += " where approval.org_id ="+orgId+" and approval = 0 and timesheet.active =1 order by  date";
	}else {
		        query += "where approval.org_id ="+orgId+" and timesheet.user_id= "+userId+" and approval = 0 and timesheet.active =1 order by  date";
	}
                query += " limit "+from+","+to;
		List<Report> updateApproval = jdbcTemplate.query(query, new BeanPropertyRowMapper(Report.class));
		return updateApproval;
	}
	public List<Report> getApprovalList(int orgId,int userId){
		String query = "select timesheet.id,approval,timesheet.user_id,DATE,PROJECT_NAME,ACTIVITY,HRS,TASK,approval.org_id " 
				+ "from (((timesheet inner join approval on approval.timesheet_id = timesheet.ID) "
				+ "inner join projects on projects.ID =  timesheet.PROJECT_ID) "
				+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID) " 
				+ "where approval.org_id ="+orgId+" and timesheet.user_id= "+userId+"  and approval = 0 and timesheet.active = 1 ";	
		if(userId == 0) {
			query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,approval.org_id "
					+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) "
					+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID)  "
					+ "inner join approval on approval.timesheet_id = timesheet.ID) "
					+ "where approval.org_id ="+orgId+" and approval = 0 and timesheet.active =1 order by  date";
		}
		List<Report> getApprovalList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Report.class));
		return getApprovalList;
	}
public List<Report> approvalPagination(int from,int to,int orgId,int userId){
		
		String query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,approval.org_id "
				+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) "
				+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID)  "
				+ "inner join approval on approval.timesheet_id = timesheet.ID) "
				+ "where approval.org_id ="+orgId+" and timesheet.user_id= "+userId+" and approval = 0 and timesheet.active =1 order by  date limit "+from+","+to;
		if(userId == 0) {
			   query = "select timesheet.USER_ID,timesheet.ID,DATE,PROJECT_ID,PROJECT_NAME,ACTIVITY,ACTIVITY_ID,HRS,TASK,approval.approval,approval.org_id "
					+ "from (((timesheet inner join projects on projects.ID =  timesheet.PROJECT_ID) "
					+ "inner join activities on activities.ID = timesheet.ACTIVITY_ID)  "
					+ "inner join approval on approval.timesheet_id = timesheet.ID) "
					+ "where approval.org_id ="+orgId+" and approval = 0 and timesheet.active =1 order by  date limit "+from+","+to;
		}
		List<Report> approvalPagination = jdbcTemplate.query(query, new BeanPropertyRowMapper(Report.class));
		return approvalPagination;
	}
}

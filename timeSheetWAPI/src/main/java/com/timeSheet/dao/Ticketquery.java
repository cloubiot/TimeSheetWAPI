package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.Status;
import com.timeSheet.model.dbentity.TroubleActivity;
import com.timeSheet.model.dbentity.TroubleTicket;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.ticket.ActivityResponse;
import com.timeSheet.model.ticket.TroubleDate;
import com.timeSheet.model.ticket.TroubleTicketDetail;

@Service
@Transactional

public class Ticketquery {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<TroubleTicket> getAllTicket(){
		String query = "select * from trouble_ticket";
		List<TroubleTicket> ticket = jdbcTemplate.query(query, new BeanPropertyRowMapper(TroubleTicket.class));
		return ticket;
	}
	
	public List<TroubleTicketDetail> getTicketDetail(int orgId,int roleId,User user){
			String query = "select trouble_ticket.*,assigned_groups.name,status.status,user.first_name,DATE_FORMAT(trouble_ticket.SLA_TIME, '%m/%d/%Y %H:%i:%s') AS SLA_DATE from trouble_ticket "
				+" inner join status on status.id = trouble_ticket.status_Id "
				+" left join user on user.id = trouble_ticket.assigned_to"
				+" left join assigned_groups on  assigned_groups.id = trouble_ticket.group_id where trouble_ticket.org_id="+orgId;
		
//		if(roleId == 3){
//			query+=" and trouble_ticket.assigned_to="+user.getId();
//		}
		if(roleId == 5){
			query +=" and trouble_ticket.contact_name ='"+user.getEmail()+"'";
		}
			query +="  order by id DESC";
		List<TroubleTicketDetail> ticketDetail = jdbcTemplate.query(query, new BeanPropertyRowMapper(TroubleTicketDetail.class));
		return ticketDetail;
	}
	public List<TroubleTicketDetail> getTicketByPagination(int  from,int to,int orgId,String ticketNumber,String contactName){
		if(ticketNumber == null)
			ticketNumber ="";
		if(contactName == null)
			contactName ="";
		String query = "select trouble_ticket.*,assigned_groups.name,status.status,user.first_name,DATE_FORMAT(trouble_ticket.SLA_TIME, '%m/%d/%Y %H:%i:%s') AS SLA_DATE from trouble_ticket "
			+" inner join status on status.id = trouble_ticket.status_Id "
			+" left join user on user.id = trouble_ticket.assigned_to"
			+" left join assigned_groups on  assigned_groups.id = trouble_ticket.group_id where trouble_ticket.org_id="+orgId+" and trouble_ticket.TICKET_NUMBER like'%"+ticketNumber+"%' and trouble_ticket.CONTACT_NAME like '%"+contactName+"%'  order by id DESC ";
		query+=" limit "+from+" ,"+to;
		List<TroubleTicketDetail> getTicketByPagination = jdbcTemplate.query(query, new BeanPropertyRowMapper(TroubleTicketDetail.class));
	return getTicketByPagination;
}
	public List<ActivityResponse> getActivity(int id){
		String query = "select user.email,user.PHONE_NUMBER,user.USER_NAME,trouble_activity.* from trouble_activity "
				+ " inner join user on user.id = trouble_activity.LAST_MOD_USER   "
				+ "where trouble_activity.ISSUE_ID ="+id+" order by id desc";
		System.out.println("w333"+query);
		List<ActivityResponse> activity = jdbcTemplate.query(query, new  BeanPropertyRowMapper(ActivityResponse.class));
		return activity;
	}
	public TroubleTicketDetail getTicketDetailById(int id){
		String query = "select trouble_ticket.*,assigned_groups.name,status.status,user.first_name,user.phone_number from trouble_ticket "
				+"inner join status on status.id = trouble_ticket.status_Id "
				+"left join assigned_groups on assigned_groups.id = trouble_ticket.group_id "
				+"left join user on user.id = trouble_ticket.assigned_to where trouble_ticket.id="+id;
		TroubleTicketDetail ticketDetail = (TroubleTicketDetail) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(TroubleTicketDetail.class));
		return ticketDetail;
	}
	public List<Status> getStatus(){
		String query = "select * from status";
		List<Status> status = jdbcTemplate.query(query, new BeanPropertyRowMapper(Status.class));
		return status;
	}
	
	public TroubleDate getById(int id){
		String query = "SELECT trouble_ticket.id,"
				+" DATE_FORMAT(trouble_ticket.OPENED_DATE, '%m/%d/%Y %H:%i:%s') AS OPENED_DATE,"
				+" DATE_FORMAT(trouble_ticket.DELIVERY_DATE, '%m/%d/%Y %H:%i:%s') AS DELIVERY_DATE,"
				+" DATE_FORMAT(trouble_ticket.SLA_TIME, '%m/%d/%Y %H:%i:%s') AS SLA_TIME,"
				+" DATE_FORMAT(trouble_ticket.COMPLETED_TIME, '%m/%d/%Y %H:%i:%s') AS COMPLETED_TIME,"
				+" DATE_FORMAT(trouble_ticket.CLOSED_TIME, '%m/%d/%Y %H:%i:%s') AS CLOSED_TIME,"
				+" DATE_FORMAT(trouble_ticket.LAST_UPDATED, '%m/%d/%Y %H:%i:%s') AS LAST_UPDATED,"
				+ "user.USER_NAME"
				+" from trouble_ticket "
				+ "inner join user on user.id = trouble_ticket.LAST_UPDATED_BY"
				+" where trouble_ticket.id="+id;
     TroubleDate trouble = (TroubleDate)jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(TroubleDate.class));
   return trouble;
}
	
	

}

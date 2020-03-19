package com.timeSheet.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
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
import com.timeSheet.clib.service.EmailService;
import com.timeSheet.clib.service.EmailTemplateService;
import com.timeSheet.clib.util.DateTimeUtil;
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.ParentChildMapping;
import com.timeSheet.model.dbentity.TroubleActivity;
import com.timeSheet.model.dbentity.TroubleTicket;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.email.EmailMessage;
import com.timeSheet.model.group.MemberListInGroup;
import com.timeSheet.model.ticket.ActivityResponse;
import com.timeSheet.model.ticket.GetActivityResponse;
import com.timeSheet.model.ticket.TicketByRoleRequest;
import com.timeSheet.model.ticket.TicketDetailResponse;
import com.timeSheet.model.ticket.TicketRequest;
import com.timeSheet.model.ticket.TicketResponse;
import com.timeSheet.model.ticket.TroubleDate;
import com.timeSheet.model.ticket.TroubleTicketDetail;
import com.timeSheet.service.GroupService;
import com.timeSheet.service.TroubleActivityService;
import com.timeSheet.service.TroubleTicketService;
import com.timeSheet.service.UserMgmtService;

@RestController
@RequestMapping("/troubleTicket")
@CrossOrigin( maxAge = 3600)
public class TroubleTicketController {

	private static final Logger logger = LoggerFactory.getLogger(TroubleTicketController.class);


	@Autowired
	TroubleTicketService troubleTicketService;
	
	@Autowired
	UserMgmtService userMgmtService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@Autowired
	TroubleActivityService activityService;
	
	
	@RequestMapping(method = RequestMethod.POST, value="/addTicket")
	public SuccessIDResponse addTicket(@RequestBody TicketRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			System.out.println("Trouble "+JSONUtil.toJson(request.getTroubleTicket()));
			System.out.println("Trouble parent Id"+JSONUtil.toJson(request.getParentId()));
			System.out.println("Trouble activity Id"+JSONUtil.toJson(request.getTroubleActivity()));
			

			TroubleTicket ticket = new TroubleTicket();
			TroubleActivity activity = new TroubleActivity();
			ParentChildMapping parentChild = new ParentChildMapping();
			Date mydate = new Date();
			

			if(request.getTroubleTicket().getId() == 0){
				ticket.setVin(request.getTroubleTicket().getVin());
				ticket.setContactName(request.getTroubleTicket().getContactName());
				ticket.setBillable(request.getTroubleTicket().getBillable());
				ticket.setBillingAddress(request.getTroubleTicket().getBillingAddress());
				ticket.setDescription(request.getTroubleTicket().getDescription());
				ticket.setTicketReason(request.getTroubleTicket().getTicketReason());
				ticket.setAssignedTo(request.getTroubleTicket().getAssignedTo());
				ticket.setGroupId(request.getTroubleTicket().getGroupId());
				ticket.setSlaTime(DateUtils.addHours(mydate,3));
				ticket.setInformationDetail(request.getTroubleTicket().getInformationDetail());
				ticket.setOpenedBy(request.getTroubleTicket().getOpenedBy());
				ticket.setOpenedDate(new Date());
				ticket.setRequestOrder(request.getTroubleTicket().getRequestOrder());
				//System.out.println("Dateee    "+DateTimeUtil.stringToDate(request.getTroubleTicket().getDeliveryDate()));
				
				ticket.setDeliveryDate(request.getTroubleTicket().getDeliveryDate());
				ticket.setStatusId(1);
				ticket.setTaxRate(request.getTroubleTicket().getTaxRate());
				ticket.setBillingRate(request.getTroubleTicket().getBillingRate());
				ticket.setProjectId(request.getTroubleTicket().getProjectId());
				ticket.setResolutionCode(request.getTroubleTicket().getResolutionCode());
				if(request.getTroubleTicket().getParentTicket() != null){
					ticket.setChildTicket(1);
					ticket.setParentTicket(request.getTroubleTicket().getParentTicket());
				}
				ticket = troubleTicketService.saveTicket(ticket);
				System.out.println(JSONUtil.toJson(ticket));
				ticket.setTicketNumber("TT00"+ticket.getId());
				troubleTicketService.saveTicket(ticket);
				
				User user = userMgmtService.getByEmail(request.getTroubleTicket().getContactName());
				TroubleTicket troubleTicket = troubleTicketService.getTicketById(ticket.getId());
				System.out.println("number 090999990990b : "+troubleTicket.getTicketNumber());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("firstName", user.getFirstName());
				map.put("number", troubleTicket.getTicketNumber());
				map.put("title", request.getTroubleTicket().getInformationDetail());
				map.put("regards", troubleTicket.getOpenedBy());
				String subject= "Your Ticket has been opened";
				String emailBody = emailTemplateService.getEmailTemplate("userTicket.vm",map);
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setToEmail(request.getTroubleTicket().getContactName());
				emailMessage.setSubject(subject);
				emailMessage.setEmailBody(emailBody);
				emailService.sendEmail(request.getTroubleTicket().getContactName(),subject , emailBody);
				if(request.getTroubleTicket().getAssignedTo() != 0){
					User assignee = userMgmtService.getUserById(request.getTroubleTicket().getAssignedTo());
					System.out.println("mail Id:  "+assignee.getEmail());
					Map<String,Object> mapAssignee = new HashMap<String,Object>();
					mapAssignee.put("firstName", assignee.getFirstName());
					mapAssignee.put("number", troubleTicket.getTicketNumber());
					mapAssignee.put("title", request.getTroubleTicket().getInformationDetail());
					mapAssignee.put("regards", troubleTicket.getOpenedBy());
					String subject1= "Your Ticket has been assigned";
					String emailBody1 = emailTemplateService.getEmailTemplate("techMail.vm",mapAssignee);
					EmailMessage emailMessage1 = new EmailMessage();
					emailMessage1.setToEmail(assignee.getEmail());
					emailMessage1.setSubject(subject1);
					emailMessage1.setEmailBody(emailBody1);
					emailService.sendEmail(assignee.getEmail(),subject1 , emailBody1);
				}
				logger.info("Ticket Added");
				}else{
		    ticket = troubleTicketService.getTicketById(request.getTroubleTicket().getId());
			ticket.setVin(request.getTroubleTicket().getVin());
			ticket.setContactName(request.getTroubleTicket().getContactName());
			ticket.setBillable(request.getTroubleTicket().getBillable());
			ticket.setBillingAddress(request.getTroubleTicket().getBillingAddress());
			ticket.setDescription(request.getTroubleTicket().getDescription());
			ticket.setTicketReason(request.getTroubleTicket().getTicketReason());
			ticket.setAssignedTo(request.getTroubleTicket().getAssignedTo());
			ticket.setGroupId(request.getTroubleTicket().getGroupId());
			if(request.getTroubleTicket().getStatusId() == 9){
				ticket.setClosedTime(new Date());
				ticket.setClosedBy(request.getTroubleTicket().getOpenedBy());
			}
			if(request.getTroubleTicket().getStatusId() == 8){
				ticket.setCompletedTime(new Date());
				ticket.setCompletedBy(request.getTroubleTicket().getOpenedBy());
			}
			ticket.setInformationDetail(request.getTroubleTicket().getInformationDetail());
			//ticket.setOpenedBy(request.getTroubleTicket().getOpenedBy());
			ticket.setDeliveryDate(request.getTroubleTicket().getDeliveryDate());
			ticket.setStatusId(request.getTroubleTicket().getStatusId());
			ticket.setBillingRate(request.getTroubleTicket().getBillingRate());
			ticket.setProjectId(request.getTroubleTicket().getProjectId());
			ticket.setTaxRate(request.getTroubleTicket().getTaxRate());
			ticket.setLastUpdated(new Date());
			ticket.setLastUpdatedBy(request.getTroubleTicket().getOpenedBy());
			ticket.setResolutionCode(request.getTroubleTicket().getResolutionCode());
			ticket.setRequestOrder(request.getTroubleTicket().getRequestOrder());
			troubleTicketService.saveTicket(ticket);
			
			if(request.getTroubleActivity().getComments() != null){
				activity.setComments(request.getTroubleActivity().getComments());
				activity.setIssueId(request.getTroubleActivity().getIssueId());
				activity.setCreatedDate(new Date());
				activity.setLastModDate(new Date());
				activity.setRoleId(request.getTroubleActivity().getRoleId());
				activity.setLastModUser(request.getTroubleActivity().getLastModUser());
				activity.setMailNotes(request.getTroubleActivity().isMailNotes());
				activity.setDescription("Note Added");
				activityService.saveActivity(activity);
				User user = userMgmtService.getByEmail(request.getTroubleTicket().getContactName());
				if(ticket.getStatusId() == 8){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("firstName", user.getFirstName());
					map.put("number", request.getTroubleTicket().getTicketNumber());
					map.put("title", request.getTroubleTicket().getInformationDetail());
					map.put("regards", request.getTroubleTicket().getOpenedBy());
					String subject= "Your Ticket has been Completed";
					String emailBody = emailTemplateService.getEmailTemplate("ticketComplete.vm",map);
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setToEmail(request.getTroubleTicket().getContactName());
					emailMessage.setSubject(subject);
					emailMessage.setEmailBody(emailBody);
					//emailService.send2EmailQueue(emailMessage);
					emailService.sendEmail(request.getTroubleTicket().getContactName(), subject, emailBody);
				}
				if(activity.isMailNotes() == true){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("firstName", user.getFirstName());
					map.put("number", request.getTroubleTicket().getTicketNumber());
					map.put("title", request.getTroubleTicket().getInformationDetail());
					map.put("notes", request.getTroubleActivity().getComments());
					map.put("regards", request.getTroubleTicket().getOpenedBy());
					String subject= "Your Ticket has been Updated";
					String emailBody = emailTemplateService.getEmailTemplate("userUpdate.vm",map);
					EmailMessage emailMessage = new EmailMessage();
					emailMessage.setToEmail(request.getTroubleTicket().getContactName());
					emailMessage.setSubject(subject);
					emailMessage.setEmailBody(emailBody);
					//emailService.send2EmailQueue(emailMessage);
					emailService.sendEmail(request.getTroubleTicket().getContactName(), subject, emailBody);
				} 
				
				}
			
			
				}	
	}catch(Exception e){
			logger.error("Ticket failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/getTicket")
	public TicketDetailResponse getTicketDetail(@RequestBody TicketByRoleRequest request){
		TicketDetailResponse response = new TicketDetailResponse();
		try{
			List<TroubleTicket> ticket = troubleTicketService.getAllTicket();
			for(TroubleTicket  tickets : ticket) {
			List<TroubleTicketDetail> ticketDetail = troubleTicketService.getTicketDetail(tickets.getAssignedTo(),request.getRoleId(),request.getUser());
			System.out.println(JSONUtil.toJson(ticketDetail));
			response.setTicketDetail(ticketDetail);
			}
			response.setCurrentDateTime(new Date());
			logger.info("Ticket");
		}
		catch(Exception e){
			logger.error("ticket failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/getTicketById/{id}")
	public TicketResponse getTicketDetailById(@PathVariable int id){
		TicketResponse response = new TicketResponse();
		try{
			TroubleTicketDetail ticketDetail = troubleTicketService.getTicketDetailById(id);
			TroubleDate troubleDate = troubleTicketService.getByTroubleDateId(id);
			troubleDate.setCurrentDateTime(DateTimeUtil.getDateAndTime());
			User user = userMgmtService.getByEmail(ticketDetail.getContactName());
			List<MemberListInGroup> group = groupService.getMemberByGroupId(ticketDetail.getGroupId());
			response.setTroubleDate(troubleDate);
			response.setGroup(group);
			response.setTicketDetail(ticketDetail);
			logger.info("Get Ticket");
		}catch(Exception e){
			logger.error("Get ticket failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getActivity/{id}")
	public GetActivityResponse getActivityById(@PathVariable int id){
		GetActivityResponse response = new GetActivityResponse();
		try{
			List<ActivityResponse> activity = troubleTicketService.getActivity(id);
			response.setActivityResponse(activity);
			logger.info("Activity by Id");
		}
		catch(Exception e){
			logger.error("Activity Failed",e);
			response.setSuccess(false);
		}
		return response;
	
}
	
	public void sendEmail(String mail,String subject,String emailBody) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setToEmail(mail);
		emailMessage.setSubject(subject);
		emailMessage.setEmailBody(emailBody);
		emailService.sendEmail(mail, subject, emailBody);
	}
}

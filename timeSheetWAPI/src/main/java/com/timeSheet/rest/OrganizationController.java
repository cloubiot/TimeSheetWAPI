package com.timeSheet.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timeSheet.clib.service.EmailService;
import com.timeSheet.clib.service.EmailTemplateService;
import com.timeSheet.clib.util.SecureData;
import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.dbentity.UserRoleMapping;
import com.timeSheet.model.email.EmailMessage;
import com.timeSheet.model.organization.OrganizationDetails;
import com.timeSheet.model.organization.OrganizationRequest;
import com.timeSheet.model.organization.OrganizationResponse;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserDetailResponse;
import com.timeSheet.service.OrganizationService;
import com.timeSheet.service.UserMgmtService;

@RestController
@RequestMapping("/organization")
@CrossOrigin( maxAge = 3600)
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addOrganization")
	public OrganizationResponse addOrganization(@RequestBody OrganizationRequest request) {
		OrganizationResponse response = new OrganizationResponse();
		
		try{
			Organization org = new Organization();
			org.setName(request.getOrg().getName());
			org.setSite(request.getOrg().getSite());
			org.setType(request.getOrg().getType());
			org.setLogo(request.getOrg().getLogo());
			org.setAddress(request.getOrg().getAddress());
			org.setCreatedDate(new Date());
			organizationService.saveOrg(org);
			addOrgUser(request,org);
			logger.info("Create Organization");
		}
		catch(Exception e){
			logger.error("create organization failied",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getOrganization")
	public OrganizationResponse getUserDetail(){
		OrganizationResponse response = new OrganizationResponse();
		try{
			List<OrganizationDetails> orgList = organizationService.getOrganization();
//			System.out.println(JSONUtil.toJson(userDetail));
			response.setOrganizationList(orgList);
			logger.info("organization list");
		}
		catch(Exception e){
			logger.error("userdetail failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	public void addOrgUser(OrganizationRequest request,Organization org) throws Exception {
		SecureData sd = new SecureData();
		User user = new User();
		user.setUserName(request.getUser().getUserName());
		user.setFirstName(request.getUser().getFirstName());
		user.setLastName(request.getUser().getLastName());
		user.setEmail(request.getUser().getEmail());
		user.setPassword(sd.encrypt(request.getUser().getPassword()));
		user.setPhoneNumber(request.getUser().getPhoneNumber());
		user.setActive("true");
		user.setCreationDate(new Date());
		user.setUpdationDate(new Date());
		user.setOrgId(org.getId());
		userMgmtService.saveUser(user);
		UserRoleMapping roleMapping = new UserRoleMapping();
		if(org.getSite().equalsIgnoreCase("https://www.cloubiot.com")) {
			roleMapping.setRoleId(1);
		}else {
			roleMapping.setRoleId(2);
		}
		roleMapping.setUserId(user.getId());
		roleMapping.setOrgId(org.getId());
		userMgmtService.saveUserRole(roleMapping);	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgName", request.getOrg().getName());
		map.put("userName", request.getUser().getUserName());
		String subject = "Timesheet Account";
		String emailBody = emailTemplateService.getEmailTemplate("orgRegister.vm",map);
		sendEmail(request.getUser().getEmail(),subject,emailBody);
	}
	
	public void sendEmail(String mail,String subject,String emailBody) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setToEmail(mail);
		emailMessage.setSubject(subject);
		emailMessage.setEmailBody(emailBody);
		emailService.sendEmail(mail, subject, emailBody);
	}
}
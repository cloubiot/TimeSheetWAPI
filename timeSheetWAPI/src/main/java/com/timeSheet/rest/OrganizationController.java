package com.timeSheet.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.clib.service.EmailService;
import com.timeSheet.clib.service.EmailTemplateService;
import com.timeSheet.clib.util.AuthUtil;
import com.timeSheet.clib.util.SecureData;
import com.timeSheet.clib.util.UuidProfile;
import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.dbentity.UserRoleMapping;
import com.timeSheet.model.email.EmailMessage;
import com.timeSheet.model.organization.OrganizationDetails;
import com.timeSheet.model.organization.OrganizationRequest;
import com.timeSheet.model.organization.OrganizationResponse;
import com.timeSheet.model.organization.SearchOrgRequest;
import com.timeSheet.model.organization.SearchOrgResponse;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserDetailResponse;
import com.timeSheet.model.usermgmt.UserSessionProfile;
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/secured/getOrganization/{userId}")
	public OrganizationResponse getUserDetail(@PathVariable int userId,HttpServletRequest servletRequest){
		OrganizationResponse response = new OrganizationResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAdminAuthorized(response,userId,servletRequest)) {
			if(!AuthUtil.isAuthorized(response,userId,servletRequest)) {
				return response;
			}
			return response;
		}
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/searchOrg")
	public SearchOrgResponse searchOrganization(@RequestBody SearchOrgRequest request,HttpServletRequest servletRequest) {
		SearchOrgResponse response = new SearchOrgResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAdminAuthorized(response,request.getUserId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		
		try{
			List<OrganizationDetails> orgDetails = organizationService.searchOrg(request.getOrgDetail().getName());
			response.setOrganizationDetails(orgDetails);
			logger.info("search organization list");
		}
		catch(Exception e){
			logger.error("search organization failied",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/orgPagination")
	public SearchOrgResponse orgPagination(@RequestBody SearchOrgRequest request,HttpServletRequest servletRequest) {
		SearchOrgResponse response = new SearchOrgResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAdminAuthorized(response,request.getUserId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		
		try{
			int from=1;
			int to=10;
			for(int i=1;i<=request.getValue();i++){
				if(i==1){
					from=0;
					to=10;
				}
				else{
					from+=10;
					to+=10;
				}
			}
			List<OrganizationDetails> orgDetails = organizationService.orgPagination(from,to,request.getName());
			response.setOrganizationDetails(orgDetails);
			logger.info("search organization list");
		}
		catch(Exception e){
			logger.error("search organization failied",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	public void addOrgUser(OrganizationRequest request,Organization org) throws Exception {
		SecureData sd = new SecureData();
		String secureToken = UUID.randomUUID().toString().replace("-", "");
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
		user.setSecureToken(secureToken);
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
	
	private void getActivity(HttpServletRequest request) {
		Cookie cookie = UuidProfile.getCookie(request, "userState");
		if(cookie != null) {
			User userToken  =  userMgmtService.getUserProfileToken(cookie.getValue());
			if(userToken != null){
				long roleId = userMgmtService.getUserRoleId(userToken.getId());
				UserSessionProfile userSessionProfile = new UserSessionProfile();
				userSessionProfile.setAdminId(roleId);
				userSessionProfile.setId(userToken.getId());
				userSessionProfile.setSecureToken(cookie.getValue());
				CacheService ehcs = new EhCacheServiceImpl();
				ehcs.putCache(cookie.getValue(), userSessionProfile);
			}
		}
	}
}

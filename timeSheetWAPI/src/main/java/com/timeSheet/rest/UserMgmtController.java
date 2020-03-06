package com.timeSheet.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.clib.model.SuccessIDResponse;
import com.timeSheet.clib.service.EmailService;
import com.timeSheet.clib.service.EmailTemplateService;
import com.timeSheet.clib.util.AuthUtil;
import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.clib.util.RandomPassword;
import com.timeSheet.clib.util.SecureData;
import com.timeSheet.clib.util.UuidProfile;
import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.dbentity.UserRole;
import com.timeSheet.model.dbentity.UserRoleMapping;
import com.timeSheet.model.email.EmailMessage;
import com.timeSheet.model.usermgmt.ActiveRequest;
import com.timeSheet.model.usermgmt.AddCustomerRequest;
import com.timeSheet.model.usermgmt.ChangePasswordRequest;
import com.timeSheet.model.usermgmt.CookieRequest;
import com.timeSheet.model.usermgmt.EmailIdExistRequest;
import com.timeSheet.model.usermgmt.EmailIdExistResponse;
import com.timeSheet.model.usermgmt.ForgotPasswordRequest;
import com.timeSheet.model.usermgmt.ForgotPasswordResponse;
import com.timeSheet.model.usermgmt.ForgotUsernameRequest;
import com.timeSheet.model.usermgmt.ForgotUsernameResponse;
import com.timeSheet.model.usermgmt.LoginRequest;
import com.timeSheet.model.usermgmt.LoginResponse;
import com.timeSheet.model.usermgmt.RoleResponse;
import com.timeSheet.model.usermgmt.SearchUserDetailRequest;
import com.timeSheet.model.usermgmt.SearchUserRequest;
import com.timeSheet.model.usermgmt.UpdateUserProfileRequest;
import com.timeSheet.model.usermgmt.UpdateUserProfileResponse;
import com.timeSheet.model.usermgmt.UserByEmailRequest;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserDetailResponse;
import com.timeSheet.model.usermgmt.UserListResponse;
import com.timeSheet.model.usermgmt.UserNameExistsRequest;
import com.timeSheet.model.usermgmt.UserNameExistsResponse;
import com.timeSheet.model.usermgmt.UserPaginationRequest;
import com.timeSheet.model.usermgmt.UserProfile;
import com.timeSheet.model.usermgmt.UserProjectRequest;
import com.timeSheet.model.usermgmt.UserSessionProfile;
import com.timeSheet.model.usermgmt.UserSignupRequest;
import com.timeSheet.model.usermgmt.UserWithRole;
import com.timeSheet.service.UserMgmtService;

@RestController
@RequestMapping("/usermgmt")
@CrossOrigin( maxAge = 3600)
public class UserMgmtController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMgmtController.class);
	
	@Autowired
	private UserMgmtService userMgmtService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/registerUser")
	public SuccessIDResponse registerUser(@RequestBody UserSignupRequest request,HttpServletRequest servletRequest) {
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			User user = new User();
			UserRoleMapping roleMapping = new UserRoleMapping();
			SecureData sd = new SecureData();
			String secureToken = UUID.randomUUID().toString().replace("-", "");
			RandomPassword newPassword = new RandomPassword();
			Organization org = userMgmtService.getOrgName(request.getOrgId());
			user = userMgmtService.getUserById(request.getId());
			if(user == null ){
				user = new User();
				user.setPassword(sd.encrypt(newPassword.generateRandomString()));
				String password = sd.decrypt(user.getPassword());
				user.setActive(request.getActive());
				user.setSecureToken(secureToken);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("firstName", request.getFirstName());
				map.put("userName", request.getEmail());
				map.put("orgName", org.getName());
				map.put("password", password );
				String subject = "Timesheet Account";
				String emailBody = emailTemplateService.getEmailTemplate("register.vm",map);
				sendEmail(request.getEmail(),subject,emailBody);
			}
			user.setUserName(request.getUserName());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			user.setPhoneNumber(request.getPhoneNumber());
			user.setActive(request.getActive());
			user.setId(request.getId());
			user.setOrgId(request.getOrgId());
			userMgmtService.saveUser(user);
			roleMapping = userMgmtService.getRoleByUserId(user.getId());
			if(roleMapping == null){
				roleMapping = new UserRoleMapping();
			}
			roleMapping.setRoleId(request.getRole());
			roleMapping.setUserId(user.getId());
			userMgmtService.saveUserRole(roleMapping);			
			logger.info("create user");
		}
		catch(Exception e){
			logger.error("create user failied",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request,HttpServletRequest servletRequest,
			HttpServletResponse res){
		LoginResponse response = new LoginResponse();
		try{
//			System.out.println("login " +request.getUserName()+" pass "+request.getPassword());
			SecureData sd = new SecureData();
			String encryptedPassword = sd.encrypt(request.getPassword());
//			System.out.println(encryptedPassword);
			UserProfile userProfile = new UserProfile();
			List<User> user = userMgmtService.login(request.getUserName(), encryptedPassword);
			try {
				Organization org = userMgmtService.orgLogin(request.getUserName(), encryptedPassword);
				response.setLogo(org.getLogo());
			}catch (Exception e) {
				logger.error("orgId not available", e);
			}
			if(!user.isEmpty()){
				response.setUser(user);
			}
			else{	
				response.setSuccess(false);
				logger.info("invalid login");
			}
			try{
				for(User retUser: user) {
				long roleId = userMgmtService.getUserRoleId(retUser.getId());
				response.setOrgId(retUser.getOrgId());
				response.setRoleId(roleId);
				UserSessionProfile userSessionProfile = new UserSessionProfile();
				userSessionProfile.setAdminId(roleId);
				userSessionProfile.setId(retUser.getId());
				userSessionProfile.setSecureToken(retUser.getSecureToken());
				userSessionProfile.setOrgId(retUser.getOrgId());
				UuidProfile.putSessionProfile(retUser.getSecureToken(),res,userSessionProfile);
				}
			}catch(Exception e){
				logger.error("roleId not available", e);
			}
		}
		catch(Exception e){
			logger.error("login Failed",e);
			response.setSuccess(false);
		}
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.POST, value = "/addUpdateAddress")
//	public SuccessIDResponse addUpdateAddress(@RequestBody AddUpdateAddressRequest request) {
//		SuccessIDResponse response = new SuccessIDResponse();
//		try {
//			System.out.println(JSONUtil.toJson(request.getAddress()));
//			Address address = userMgmtService.addUpdateAddress(request.getAddress());
//			response.setId(address.getId());
//			logger.info("Add update address success");
//		} catch (Exception e) {
//			logger.error("Add update address failed", e);
//			response.setSuccess(false);
//		}
//		return response;
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/userNameExists")
	public UserNameExistsResponse userNameExists(@RequestBody UserNameExistsRequest request){
		UserNameExistsResponse response = new UserNameExistsResponse();
		try{
			User user = userMgmtService.getByUserName(request.getUserName());
			if(user != null)
				response.setUserNameExists(true);
			else
				response.setUserNameExists(false);
				response.setSuccess(false);
		}
		catch(Exception e){
			logger.error("userName exists failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/emailIdExist")
	public EmailIdExistResponse emailIdExist(@RequestBody EmailIdExistRequest request){
		EmailIdExistResponse response = new EmailIdExistResponse();
		try{
			User user = userMgmtService.getByEmail(request.getEmailId());
			if(user != null)
				response.setEmailIdExist(true);
			else
				response.setEmailIdExist(false);
		}
		catch(Exception e){
			logger.error("invalid EmailId", e);
			response.setSuccess(false);
		}
		
		return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/emailIdExistById")
	public EmailIdExistResponse emailIdExistById(@RequestBody EmailIdExistRequest request){
		EmailIdExistResponse response = new EmailIdExistResponse();
		try{
//			System.out.println("checking emailid user "+request.getUserId());
			if(request.getUserId() != 0){
				User user = userMgmtService.emailIdExists(request.getEmailId(),request.getUserId());
				if(user != null)
					response.setEmailIdExist(false);
				else
					response.setEmailIdExist(true);
			}
			if(request.getUserId() == 0){
				User user = userMgmtService.getByEmail(request.getEmailId());
				if(user != null)
					response.setEmailIdExist(true);
				else
					response.setEmailIdExist(false);
			}
		}
		catch(Exception e){
			logger.error("invalid EmailId", e);
			response.setSuccess(false);
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/orgExist")
	public EmailIdExistResponse organizationExist(@RequestBody EmailIdExistRequest request){
		EmailIdExistResponse response = new EmailIdExistResponse();
		try{
			
			Organization orgExist = userMgmtService.getByName(request.getName());
				if(orgExist != null)
					response.setOrgExist(true);
				else
					response.setOrgExist(false);
		}
		catch(Exception e){
			logger.error("invalid organization", e);
			response.setSuccess(false);
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/siteExist")
	public EmailIdExistResponse websiteExist(@RequestBody EmailIdExistRequest request){
		EmailIdExistResponse response = new EmailIdExistResponse();
		try{
			
			Organization siteExist = userMgmtService.getBySite(request.getWebsite());
				if(siteExist != null)
					response.setWebsiteExist(true);
				else
					response.setWebsiteExist(false);
		}
		catch(Exception e){
			logger.error("invalid website", e);
			response.setSuccess(false);
		}
		
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/forgotPassword")
	public ForgotPasswordResponse forgotPassword(@RequestBody ForgotPasswordRequest request){
		ForgotPasswordResponse response = new ForgotPasswordResponse();
		try{
			User user = userMgmtService.getByUserName(request.getUserName());
			SecureData sd = new SecureData();
			String password = sd.decrypt(user.getPassword());
			response.setPassword(password);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("userName", user.getUserName());
			map.put("password", password );
			String subject = "Your password for Timesheet ";
			String emailBody = emailTemplateService.getEmailTemplate("recoveryPassword.vm",map);
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setToEmail(user.getEmail());
			emailMessage.setSubject(subject);
			emailMessage.setEmailBody(emailBody);
			emailService.sendEmail(user.getEmail(), subject, emailBody);
			//emailService.send2EmailQueue(emailMessage);
		}
		catch(Exception e){
			logger.error("forgot password failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/forgotUsername")
	public ForgotUsernameResponse forgotUsername(@RequestBody ForgotUsernameRequest request){
		ForgotUsernameResponse response = new ForgotUsernameResponse();
		try{
			User user = userMgmtService.getByEmail(request.getEmail());
			if(user != null){
				response.setUserName(user.getUserName());
				response.isSuccess();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("firstName", user.getFirstName());
				map.put("lastName", user.getLastName());
				map.put("userName", user.getUserName());
				String subject = "Your username for DCSolar ";
				String emailBody = emailTemplateService.getEmailTemplate("recoveryUsername.vm",map);
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setToEmail(user.getEmail());
				emailMessage.setSubject(subject);
				emailMessage.setEmailBody(emailBody);
				emailService.sendEmail(user.getEmail(), subject, emailBody);
				//emailService.send2EmailQueue(emailMessage);
				
			}
		}
		catch(Exception e){
			logger.error("forgot usernamefailed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/changePassword")
	public SuccessIDResponse changePassword(@RequestBody ChangePasswordRequest request,HttpServletRequest servletRequest){
		SuccessIDResponse response = new SuccessIDResponse();
		getActivity(servletRequest);
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
//		System.out.println("#####"+JSONUtil.toJson(request));
		try{
//			System.out.println(request.getUserId());
			SecureData sd = new SecureData();
			String oldPassword = sd.encrypt(request.getOldPassword());
//			System.out.println(oldPassword);
			SecureData sd1 = new SecureData();
			String newPassword = sd1.encrypt(request.getNewPassword());
//			System.out.println(newPassword);
			User user = userMgmtService.getByIdAndPassword(request.getUserId(), oldPassword);
//			System.out.println("###"+JSONUtil.toJson(user));
			if(user == null){
				response.setSuccess(false);
			}
			else{
				user.setPassword(newPassword);
				response.setId(user.getId());
				logger.info("password changed");
			}
			user.setPassword(newPassword);
			userMgmtService.saveUser(user);
		}
		catch(Exception e){
			logger.error("change Password Failed",e);
		}
		return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/secured/updateUserProfile")
	public UpdateUserProfileResponse updateUserProfile(@RequestBody UpdateUserProfileRequest request,HttpServletRequest servletRequest){
		UpdateUserProfileResponse response = new UpdateUserProfileResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isAuthorized(response,request.getUser().getId(),servletRequest)) {
			return response;
		}
		try{
			User user = userMgmtService.getUserById(request.getUser().getId());
			user.setEmail(request.getUser().getEmail());
			user.setFirstName(request.getUser().getFirstName());
			user.setLastName(request.getUser().getLastName());
			user.setPhoneNumber(request.getUser().getPhoneNumber());
			user.setProfileImageUrl(request.getUser().getProfileImageUrl());
			userMgmtService.saveUser(user);
			/*Map<String,Object> map = new HashMap<String,Object>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("userName", user.getUserName());
			String subject= "Your Profile has been updated Successfully";
			String emailBody = emailTemplateService.getEmailTemplate("userProfileUpdate.vm",map);
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setToEmail(user.getEmail());
			emailMessage.setSubject(subject);
			emailMessage.setEmailBody(emailBody);
			emailService.send2EmailQueue(emailMessage);*/
			logger.info("successfully update");
			user.setPassword("");
			response.setUser(user);

		}
		catch(Exception e){
			logger.error("invalid userId",e);
			response.setSuccess(false);
		}
		return response;
}
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getUserList")
	public UserListResponse getUserList(@RequestBody UserProjectRequest request,HttpServletRequest servletRequest){
		UserListResponse response = new UserListResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<UserWithRole> user = userMgmtService.getUserList(request.getProjectId(),request.getOrgId());
			response.setUser(user);
			logger.info("user List");
		}
		catch(Exception e){
			logger.error("List failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchUser")
	public UserListResponse getUserList(@RequestBody SearchUserRequest request){
		UserListResponse response = new UserListResponse();
		try{
			List<UserWithRole> user = userMgmtService.searchUser(request.getName(),request.getRoleId());
			response.setUser(user);
//			System.out.println(JSONUtil.toJson(user));
			logger.info("search user List");
		}
		catch(Exception e){
			logger.error("List failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getUserDetails")
	public UserDetailResponse getUserDetail(@RequestBody ActiveRequest request,HttpServletRequest servletRequest){
		UserDetailResponse response = new UserDetailResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getOrgId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<UserDetail> userDetail = userMgmtService.getUserDetail(request.getOrgId());
//			System.out.println(JSONUtil.toJson(userDetail));
			response.setUserDetail(userDetail);
			logger.info("user Details");
		}
		catch(Exception e){
			logger.error("userdetail failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/activeUser")
	public SuccessIDResponse changeActive(@RequestBody ActiveRequest request){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			User user = userMgmtService.getUserById(request.getUserId());
			user.setActive(request.getActive());
			userMgmtService.saveUser(user);
			response.setSuccess(true);
			logger.info("Active changed");
		}
		catch(Exception e){
			logger.error("Active failed");
			response.setSuccess(false);
		}
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/getUser")
	public UserProfile getUser(@RequestBody UserProjectRequest request,HttpServletRequest servletRequest){
		UserProfile response = new UserProfile();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			User user = userMgmtService.getUserById(request.getUserId());
			response.setUser(user);
			UserRoleMapping userRole = userMgmtService.getRoleByUserId(request.getUserId());
//			System.out.println(" "+JSONUtil.toJson(userRole));
			response.setRoleId(userRole.getRoleId());
			logger.info("Get full detail for user");
		}
		catch(Exception e){
			logger.error("Get Full User Failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/getUserByPagination")
	public UserDetailResponse getUserByPagination(@RequestBody UserPaginationRequest request,HttpServletRequest servletRequest){
		UserDetailResponse response = new UserDetailResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
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
			List<UserDetail> userDetail = userMgmtService.getUserByPagination(from, to,request.getEmail(),request.getName(),request.getOrgId());
//			System.out.println(JSONUtil.toJson(userDetail));
			response.setUserDetail(userDetail);
			logger.info("user Details");
		}
		catch(Exception e){
			logger.error("userdetail failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/secured/searchUserDetail")
	public UserDetailResponse searchUserDetail(@RequestBody SearchUserDetailRequest request,HttpServletRequest servletRequest){
		UserDetailResponse response = new UserDetailResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<UserDetail> userDetail = userMgmtService.searchUserDetail(request.getUserDetail().getFirstName(),request.getUserDetail().getEmail(), 
											request.getUserDetail().getPhoneNumber(),request.getUserDetail().getDesc(),request.getUserDetail().getActive(),request.getOrgId());
			response.setUserDetail(userDetail);
			logger.info("search user detail");
		}
		catch(Exception e){
			logger.error("search user failed",e);
			response.setSuccess(false);
		}
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/getActiveUser/{value}")
	public UserDetailResponse getActiveUser(@PathVariable String value){
		UserDetailResponse response = new UserDetailResponse();
		try{
			List<UserDetail> userDetail = userMgmtService.getActiveUser(value);
			response.setUserDetail(userDetail);
			logger.info("search Active user detail");
		}
		catch(Exception e){
			logger.error("search Active user failed",e);
			response.setSuccess(false);
		}
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="generatePassword/{id}")
	public SuccessIDResponse generatePassword(@PathVariable int id){
		SuccessIDResponse response = new SuccessIDResponse();
		try{
			RandomPassword randomString = new RandomPassword();
			String password = randomString.generateRandomString(); 
			SecureData sd = new SecureData();
			String encrypt = sd.encrypt(password);
			User user = userMgmtService.getUserById(id);
			user.setPassword(encrypt);
			userMgmtService.saveUser(user);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("firstName", user.getFirstName());
			map.put("userName", user.getEmail());
			map.put("password", password );
			String subject = "Your Password for DCSolar";
			String emailBody = emailTemplateService.getEmailTemplate("generatePassword.vm",map);
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setToEmail(user.getEmail());
			emailMessage.setSubject(subject);
			emailMessage.setEmailBody(emailBody);
			emailService.sendEmail(user.getEmail(), subject, emailBody);
//			emailService.send2EmailQueue(emailMessage);
		}
		catch(Exception e){
			
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/secured/getUserByProjectId")
	public UserDetailResponse getUserByProjectId(@RequestBody ActiveRequest request,HttpServletRequest servletRequest){
		UserDetailResponse response = new UserDetailResponse();
		getActivity(servletRequest);
		if(!AuthUtil.isOrgAuthorized(response,request.getUserId(),request.getOrgId(),servletRequest)) {
			if(!AuthUtil.isAuthorized(response,request.getUserId(),servletRequest)) {
				return response;
			}
			return response;
		}
		try{
			List<UserDetail> userDetail = userMgmtService.getUserByProjectId(request.getProjectId());
			response.setUserDetail(userDetail);
			logger.info("user success");
		}
		catch(Exception e){
			response.setSuccess(false);
			logger.error("user failed",e);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/secured/getRoles")
	public RoleResponse getRoles(HttpServletRequest servletRequest){
		RoleResponse response = new RoleResponse();
		try{
			List<UserRole> userRole = userMgmtService.getRoles();
			response.setUserRole(userRole);
			logger.info("user role");
			}
		catch(Exception e){
			response.setSuccess(false);
			logger.error("user role failed",e);
		}
		return response;
	}
	
	@RequestMapping( method = RequestMethod.POST, value = "/getUserActive")
	public LoginResponse getUserCookies(@RequestBody CookieRequest request,HttpServletRequest servletRequest){
		logger.info("Comming inside user activity");
		LoginResponse response = new LoginResponse();
		try{
			  if(request.getValue() != null) {  
				List<User> user = userMgmtService.getUserActive(request.getValue());
				response.setUser(user);
				try{
				Organization org = userMgmtService.orgLogo(request.getValue());
				response.setLogo(org.getLogo());
				}catch (Exception e) {
					logger.error("orgId not available", e);
				}
				
				/* add login histoory */
//				UserSessionProfile userSessionProfile = new UserSessionProfile();
//				userSessionProfile.setId(userActive.getUserId());
//				userSessionProfile.setSecureToken(request.getValue());
//				servletRequest.getSession().setAttribute("UserSessionProfile", userSessionProfile);
//				Cookie cookie = UuidProfile.getCookie(servletRequest, "AviationUUID");
//				if(cookie != null) {
//				UuidProfile.putCache((cookie == null ? null :cookie.getValue()), userSessionProfile);
//				}else {
//					Cookie cookie3 = UuidProfile.setCookie("AviationUUID",request.getValue());
//					response.addCookie(cookie3);
//					UuidProfile.putCache(cookie3.getValue(), userSessionProfile);		
//				}
				
				
				try{
					for(User retUser: user) {
					long roleId = userMgmtService.getUserRoleId(retUser.getId());
					response.setOrgId(retUser.getOrgId());
					response.setRoleId(roleId);
					UserSessionProfile userSessionProfile1 = new UserSessionProfile();
					userSessionProfile1.setAdminId(roleId);
					userSessionProfile1.setId(retUser.getId());
					userSessionProfile1.setSecureToken(request.getValue());
					Cookie cookie1 = UuidProfile.getCookie(servletRequest, "userState");
					if(cookie1 != null) {
					CacheService cs = new EhCacheServiceImpl();
					cs.putCache((cookie1 == null ? null :cookie1.getValue()), userSessionProfile1);
					}else {
//					Cookie cookie2 = UuidProfile.setCookie("AviationUUID",request.getValue());
//					response.addCookie(cookie2);
//					UuidProfile.putCache(cookie2.getValue(), userSessionProfile1);	
					UuidProfile.putSessionProfile(request.getValue(),response,userSessionProfile1);
					}
//					servletRequest.getSession().setAttribute("adminUser", userSessionProfile1);
				}
				}catch(Exception e){
					logger.error("roleId not available", e);
				}
				
			}
			
		}
		catch(Exception e){
			logger.error("active failed",e);
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
	
	private void getActivity(HttpServletRequest request) {
		Cookie cookie = UuidProfile.getCookie(request, "userState");
		if(cookie != null) {
			User userToken  =  userMgmtService.getUserProfileToken(cookie.getValue());
			if(userToken != null){
				long roleId = userMgmtService.getUserRoleId(userToken.getId());
				UserSessionProfile userSessionProfile = new UserSessionProfile();
				userSessionProfile.setAdminId(roleId);
				userSessionProfile.setId(userToken.getId());
				userSessionProfile.setOrgId(userToken.getOrgId());
				userSessionProfile.setSecureToken(cookie.getValue());
				CacheService ehcs = new EhCacheServiceImpl();
				ehcs.putCache(cookie.getValue(), userSessionProfile);
			}
		}
	}
}

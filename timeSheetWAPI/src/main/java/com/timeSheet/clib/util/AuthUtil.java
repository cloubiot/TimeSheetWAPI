package com.timeSheet.clib.util;

import javax.servlet.http.HttpServletRequest;

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.clib.model.BaseResponse;
import com.timeSheet.model.usermgmt.UserSessionProfile;

public class AuthUtil {
	
	public static boolean isAuthorized(BaseResponse baseResponse,int userId,HttpServletRequest request) {
//		UserSessionProfile userSessionProfile =   (UserSessionProfile) request.getSession().getAttribute("UserSessionProfile");
		CacheService cs = new EhCacheServiceImpl();
		UserSessionProfile userSessionProfile = cs.getCache(request);
		if(userSessionProfile != null) {
  		if( userSessionProfile.getId() != userId) {
				baseResponse.setSuccess(false);
				baseResponse.setUserErrorMsg("Not Authorized");
			}
		}else {	
			baseResponse.setSuccess(false);
			baseResponse.setUserErrorMsg("Not Authorized");
		}
		return baseResponse.isSuccess();
	}
	
	public static  boolean isOrgAuthorized(BaseResponse baseResponse,int userId,HttpServletRequest request) {
//		UserSessionProfile adminUser =   (UserSessionProfile) request.getSession().getAttribute("adminUser");
		CacheService cs = new EhCacheServiceImpl();
		UserSessionProfile adminUser = cs.getCache(request);
		System.out.println("#####"+JSONUtil.toJson(adminUser));
		if(adminUser != null) {
			if(adminUser.getAdminId() > 3) {
				baseResponse.setSuccess(false);
				baseResponse.setUserErrorMsg("Not Authorized");
			}
		}else {
			baseResponse.setSuccess(false);
			baseResponse.setUserErrorMsg("Not Authorized");
		}
		return baseResponse.isSuccess();

	}
	
	public static  boolean isAdminAuthorized(BaseResponse baseResponse,int userId,HttpServletRequest request) {
//		UserSessionProfile adminUser =   (UserSessionProfile) request.getSession().getAttribute("adminUser");
		CacheService cs = new EhCacheServiceImpl();
		UserSessionProfile adminUser = cs.getCache(request);
		System.out.println("#####"+JSONUtil.toJson(adminUser));
		if(adminUser != null) {
			if(adminUser.getAdminId() > 1) {
				baseResponse.setSuccess(false);
				baseResponse.setUserErrorMsg("Not Authorized");
			}
		}else {
			baseResponse.setSuccess(false);
			baseResponse.setUserErrorMsg("Not Authorized");
		}
		return baseResponse.isSuccess();

	}
}

package com.timeSheet.clib.cache;

import javax.servlet.http.HttpServletRequest;

import com.timeSheet.model.usermgmt.UserSessionProfile;


public interface CacheService {
	
	 public UserSessionProfile getCache(HttpServletRequest request);
     public void putCache(String key, UserSessionProfile value);
     
}

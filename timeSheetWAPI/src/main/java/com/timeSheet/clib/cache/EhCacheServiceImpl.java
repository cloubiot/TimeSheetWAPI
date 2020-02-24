package com.timeSheet.clib.cache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.clib.util.UuidProfile;
import com.timeSheet.model.usermgmt.UserSessionProfile;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheServiceImpl implements CacheService{
	
	public void putCache(String key,UserSessionProfile value) {
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("userProfileCache");
		System.out.println("^^^^^^"+JSONUtil.toJson(value));
		cache.put(new Element(key, value));
	}
	
	public  UserSessionProfile getCache(HttpServletRequest request) {
		Cookie cookie = UuidProfile.getCookie(request, "userState");
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("userProfileCache");
		Element element = cache.get((cookie == null ? null :cookie.getValue()));
		UserSessionProfile userSessionProfile = (element == null ? null : (UserSessionProfile) element.getObjectValue());
		return userSessionProfile;
	}
}

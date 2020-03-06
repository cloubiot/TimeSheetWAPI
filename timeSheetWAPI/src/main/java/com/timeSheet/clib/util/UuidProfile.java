package com.timeSheet.clib.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.model.usermgmt.UserSessionProfile;


public class UuidProfile {
	
	public static Cookie setCookie(String cookieName, String cookieValue) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
	    cookie.setPath("/");
	    cookie.setMaxAge(60 * 24 * 60 * 60);
		return cookie;
	}
	
	public static void setSession(HttpServletRequest servletRequest,String key, UserSessionProfile userSessionProfile) {
		servletRequest.getSession().setAttribute(key, userSessionProfile);
	}
	
//	public static void putCache(String key,UserSessionProfile value) {
//		CacheManager manager = CacheManager.create();
//		Cache cache = manager.getCache("userProfileCache");
//		cache.put(new Element(key, value));
//	}
	
	public static void putSessionProfile(String secureToken,HttpServletResponse response,UserSessionProfile userSessionProfile) {
		Cookie cookie = setCookie("userState",secureToken);
        response.addCookie(cookie);
        CacheService cs = new EhCacheServiceImpl();
        cs.putCache(cookie.getValue(), userSessionProfile);
//		UuidProfile.putCache(cookie.getValue(), userSessionProfile);
	}
	
//	public static UserSessionProfile getSessionProfile(HttpServletRequest request) {
//		Cookie cookie = UuidProfile.getCookie(request, "AviationUUID");
//		CacheManager manager = CacheManager.create();
//		Cache cache = manager.getCache("userProfileCache");
//		Element element = cache.get((cookie == null ? null :cookie.getValue()));
//		UserSessionProfile userSessionProfile = (element == null ? null : (UserSessionProfile) element.getObjectValue());
//		return userSessionProfile;
//	}
	public static Cookie getCookie(HttpServletRequest request, String name) {
		 Cookie[] cookies = request.getCookies();
//		 Cookie ck = new Cookie(name,"155e571c5b1f49ce9b95d7259495583a");
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equals(name)) {
		                return cookie;
		            }
		        }
		    }
		    return null;
	}

}

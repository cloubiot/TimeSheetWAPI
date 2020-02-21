package com.timeSheet.clib.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.timeSheet.clib.cache.CacheService;
import com.timeSheet.clib.cache.EhCacheServiceImpl;
import com.timeSheet.model.usermgmt.UserSessionProfile;




public class MyCounterInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {

        String url = request.getRequestURI();
        CharSequence secured = "secured";
        boolean securedUrl = url.contains(secured); 
//		UserSessionProfile output = (element == null ? null : (UserSessionProfile) element.getObjectValue());
        CacheService cs = new EhCacheServiceImpl();
        UserSessionProfile logInFlag = cs.getCache(request);
        System.out.println("2222222-- :"+JSONUtil.toJson(securedUrl));
        System.out.println("1111111-- :"+JSONUtil.toJson(logInFlag));
//		System.out.println("UserSessionProfile: "+JSONUtil.toJson(logInFlag));
        String userSessionProfile = String.valueOf(logInFlag); 
		if(securedUrl) {
			if(userSessionProfile == null) {
		     return false;
			}
		}
		return true;
    }
}

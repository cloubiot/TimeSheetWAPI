package com.beanstree.dcsolar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.usermgmt.LoginRequest;
import com.timeSheet.rest.UserMgmtController;

@Component

public class RequestInterceptor extends HandlerInterceptorAdapter {
	private static final long MAX_INACTIVE_SESSION_TIME = 30 * 1000;
	
	private static final long serialVersionUID = 1L;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
				Object object) throws IOException {
		
//		final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
//		String api = request.getRequestURI();
//		
//		
//		if(api.equalsIgnoreCase("/avellaWAPI/usermgmt/login")){
//			System.out.println("while login");
//			
//		}
//		else{
//			 HttpSession session = request.getSession(true);
//			//HttpSession session = request.getSession();
//			 Object  userName=(String)session.getAttribute("user");
//			 System.out.println("ooo "+userName);
//			System.out.println("session "+session.getAttribute("user"));
//			if(session == null){
//				System.out.println("enter");
//				response.sendRedirect("http://localhost:4200/#/home");
//	            
//				return false;
//			}
//			else
//				return true;
//		}
		
		return true;
			
		
	}


	 /*@Override
	 public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object object, ModelAndView model)
			throws Exception {
		System.out.println("_________________________________________");
		System.out.println("In postHandle request processing "
				+ "completed by @RestController");
		System.out.println("_________________________________________");
	 }

	 @Override
	 public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object object, Exception arg3)
			throws Exception {
		System.out.println("________________________________________");
		System.out.println("In afterCompletion Request Completed");
		System.out.println("________________________________________");
	 }*/
}

package com.beanstree.dcsolar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.timeSheet.clib.util.MyCounterInterceptor;


@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan({"com.timeSheet.*"})
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	RequestInterceptor requestInterceptor;
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		/*
		System.out.println("####CORS ADDED..");
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("PUT", "DELETE","GET","POST")
			.allowedHeaders("header1", "header2", "header3")
			.exposedHeaders("header1", "header2")
			.allowCredentials(false).maxAge(3600);
			*/
	}
	 @Override
	    public void addInterceptors (InterceptorRegistry registry) {
		 
	        registry.addInterceptor(new MyCounterInterceptor());
	 }
	/*@Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(requestInterceptor);
    }*/
}
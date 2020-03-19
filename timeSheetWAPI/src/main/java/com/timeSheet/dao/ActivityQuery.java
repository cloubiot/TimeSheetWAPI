package com.timeSheet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.model.dbentity.TroubleActivity;



@Service
@Transactional
public class ActivityQuery {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<TroubleActivity> getActivity(int id){
		String query = "select * from activity where issue_id="+id+" order by id desc";
		List<TroubleActivity> activity = jdbcTemplate.query(query, new  BeanPropertyRowMapper(TroubleActivity.class));
		System.out.println(JSONUtil.toJson(activity));
		return activity;
	}
}

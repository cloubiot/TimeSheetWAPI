package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.dao.ActivityQuery;
import com.timeSheet.dao.TroubleActivityRepository;
import com.timeSheet.model.dbentity.TroubleActivity;

@Service
@Transactional
public class TroubleActivityService {

	@Autowired
	TroubleActivityRepository activityRepository;
	
	@Autowired
	ActivityQuery activityQuery;
	
	public TroubleActivity saveActivity(TroubleActivity activity){
		return this.activityRepository.save(activity);
	}
	public TroubleActivity getActivityById(int id){
		return this.activityRepository.findById(id);
	}
	public List<TroubleActivity> getActivity(int id) {
		return this.activityQuery.getActivity(id);
	}
}

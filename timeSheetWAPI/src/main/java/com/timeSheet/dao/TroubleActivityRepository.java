package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.dbentity.TroubleActivity;

public interface TroubleActivityRepository extends CrudRepository <TroubleActivity, Long>{

	TroubleActivity findById(int id);
	List<TroubleActivity> findAll();
}

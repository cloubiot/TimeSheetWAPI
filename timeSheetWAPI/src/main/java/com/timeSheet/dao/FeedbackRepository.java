package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Approval;
import com.timeSheet.model.usermgmt.Feedback;

public interface FeedbackRepository  extends CrudRepository<Feedback,Long> {

}

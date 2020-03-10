package com.timeSheet.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Approval;

public interface ApprovalRepository extends CrudRepository<Approval,Long>{

	Approval findByTimesheetId(int id);
}

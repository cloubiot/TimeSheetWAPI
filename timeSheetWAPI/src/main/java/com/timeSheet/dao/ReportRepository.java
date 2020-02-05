package com.timeSheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.timeSheet.model.timesheet.Report;




public interface ReportRepository extends CrudRepository<Report, Long>{



}

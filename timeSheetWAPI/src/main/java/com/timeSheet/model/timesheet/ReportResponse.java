package com.timeSheet.model.timesheet;

import java.util.List;

import com.timeSheet.clib.model.BaseResponse;

public class ReportResponse extends BaseResponse{

	List<Report> report;
	
	List<Project> project;
	
	List<ReportList> reportlist;
	
	List<Activities>  activity;
	
	List<Reportview> reportview;

	Activities activities;
	
	public List<Report> getReport() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public List<ReportList> getReportlist() {
		return reportlist;
	}

	public void setReportlist(List<ReportList> reportlist) {
		this.reportlist = reportlist;
	}

	public List<Activities> getActivity() {
		return activity;
	}

	public void setActivity(List<Activities> activity) {
		this.activity = activity;
	}

	public List<Reportview> getReportview() {
		return reportview;
	}

	public void setReportview(List<Reportview> reportview) {
		this.reportview = reportview;
	}

	public Activities getActivities() {
		return activities;
	}

	public void setActivities(Activities activities) {
		this.activities = activities;
	}

	
	
	
}

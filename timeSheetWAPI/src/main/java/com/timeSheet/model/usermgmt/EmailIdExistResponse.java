package com.timeSheet.model.usermgmt;

import com.timeSheet.clib.model.BaseResponse;

public class EmailIdExistResponse extends BaseResponse{

	boolean emailIdExist;
	boolean orgExist;
	boolean websiteExist;

	public boolean isEmailIdExist() {
		return emailIdExist;
	}

	public void setEmailIdExist(boolean emailIdExist) {
		this.emailIdExist = emailIdExist;
	}

	public boolean isOrgExist() {
		return orgExist;
	}

	public void setOrgExist(boolean orgExist) {
		this.orgExist = orgExist;
	}

	public boolean isWebsiteExist() {
		return websiteExist;
	}

	public void setWebsiteExist(boolean websiteExist) {
		this.websiteExist = websiteExist;
	}
	
	
}

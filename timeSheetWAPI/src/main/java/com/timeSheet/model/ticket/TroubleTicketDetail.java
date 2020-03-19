package com.timeSheet.model.ticket;

import com.timeSheet.model.dbentity.TroubleTicket;

public class TroubleTicketDetail extends TroubleTicket {

	String status;
	int addressId;
	String firstName;
	int slaFlag;
	String slaDate;
	String name;
	String imgLocation;
	String phoneNumber;
	int parentId;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getSlaFlag() {
		return slaFlag;
	}

	public void setSlaFlag(int slaFlag) {
		this.slaFlag = slaFlag;
	}

	public String getSlaDate() {
		return slaDate;
	}

	public void setSlaDate(String slaDate) {
		this.slaDate = slaDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	

	
}

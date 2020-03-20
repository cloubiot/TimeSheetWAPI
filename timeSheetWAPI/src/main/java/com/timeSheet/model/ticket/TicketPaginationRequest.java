package com.timeSheet.model.ticket;

public class TicketPaginationRequest {
int value;
    int orgId;
    int userId;
    String ticketNumber;
String contactName;
public int getValue() {
return value;
}
public void setValue(int value) {
this.value = value;
}
public int getOrgId() {
return orgId;
}
public void setOrgId(int orgId) {
this.orgId = orgId;
}
public int getUserId() {
return userId;
}
public void setUserId(int userId) {
this.userId = userId;
}
public String getTicketNumber() {
return ticketNumber;
}
public void setTicketNumber(String ticketNumber) {
this.ticketNumber = ticketNumber;
}
public String getContactName() {
return contactName;
}
public void setContactName(String contactName) {
this.contactName = contactName;
}


}

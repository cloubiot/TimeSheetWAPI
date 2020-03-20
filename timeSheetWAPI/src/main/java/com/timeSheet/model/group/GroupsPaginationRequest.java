package com.timeSheet.model.group;
import com.timeSheet.model.dbentity.AssignedGroups;

public class GroupsPaginationRequest {

int value;
String name;
    int orgId;
    int userId;
    AssignedGroups groups;
   
public AssignedGroups getGroups() {
return groups;
}
public void setGroups(AssignedGroups groups) {
this.groups = groups;
}
public int getValue() {
return value;
}
public void setValue(int value) {
this.value = value;
}

public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
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
   
   
}
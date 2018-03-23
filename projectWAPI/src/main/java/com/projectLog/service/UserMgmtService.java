package com.projectLog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectLog.clib.util.JSONUtil;

import com.projectLog.dao.UserMgmtRepository;
import com.projectLog.dao.UserQuery;
import com.projectLog.dao.UserRoleMappingRepository;

import com.projectLog.model.dbentity.User;
import com.projectLog.model.dbentity.UserRoleMapping;

import com.projectLog.model.usermgmt.UserDetail;
import com.projectLog.model.usermgmt.UserWithRole;

@Service
@Transactional
public class UserMgmtService {

	@Autowired
	UserMgmtRepository userMgmtRepository;
	@Autowired
	UserRoleMappingRepository userRoleMappingRepository;
	
	@Autowired
	UserQuery userQuery;
	
	public User saveUser(User user){
		return this.userMgmtRepository.save(user);
	}
	
	public User login(String email,String password){
		return this.userQuery.login(email, password);	}
	
	
	public User getByUserName(String userName){
		return this.userMgmtRepository.findByEmail(userName);
	}
	
	public User getByEmail(String email){
		return this.userMgmtRepository.findByEmail(email);
	}
	public User emailIdExists(String email,int userId){
		return this.userQuery.emailIdExists(email, userId);
	}
	public User getByIdAndPassword(int id,String password){
		return this.userMgmtRepository.findByIdAndPassword(id, password);
	}
	
	public long getUserRoleId(long userId){
		return this.userQuery.getUserRoleId(userId);
	}
	
	public User getUserById(int id){
		return this.userMgmtRepository.findById(id);
	}
	
	public List<UserWithRole> getUserList(){
		return this.userQuery.getUserList();
	}
	
	public List<UserWithRole> searchUser(String name,int roleId){
		return this.userQuery.searchUser(name,roleId);
	}
	public List<UserDetail> getUserDetail(){
		return this.userQuery.getUserDetail();
	}
	
	public UserRoleMapping saveUserRole(UserRoleMapping userRole){
		return this.userRoleMappingRepository.save(userRole);
	}
	public UserRoleMapping getRoleByUserId(int id){
		return this.userRoleMappingRepository.findByUserId(id);
	}
	public List<UserDetail> getUserByPagination(int from,int to){
		return this.userQuery.getPaginationForUser(from, to);
	}
	public List<UserDetail> searchUserDetail(String name,String mailId,String phoneNumber,String role,String active){
		return this.userQuery.searchUserDetail(name, mailId, phoneNumber, role,active);
	}
	public List<UserDetail> getActiveUser(String value){
		return this.userQuery.getActiveUser(value);
	}
	public List<UserDetail> getUserByProjectId(int projectId){
		return this.userQuery.getUserByProjectId(projectId);
	}
}

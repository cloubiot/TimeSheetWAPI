package com.timeSheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeSheet.clib.util.JSONUtil;
import com.timeSheet.dao.OrganizationRepository;
import com.timeSheet.dao.UserMgmtRepository;
import com.timeSheet.dao.UserQuery;
import com.timeSheet.dao.UserRoleMappingRepository;
import com.timeSheet.dao.UserRoleRepository;
import com.timeSheet.model.dbentity.Organization;
import com.timeSheet.model.dbentity.User;
import com.timeSheet.model.dbentity.UserRole;
import com.timeSheet.model.dbentity.UserRoleMapping;
import com.timeSheet.model.usermgmt.UserDetail;
import com.timeSheet.model.usermgmt.UserWithRole;

@Service
@Transactional
public class UserMgmtService {

	@Autowired
	UserMgmtRepository userMgmtRepository;
	@Autowired
	UserRoleMappingRepository userRoleMappingRepository;
	
	@Autowired
	UserQuery userQuery;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	
	
	public User saveUser(User user){
		return this.userMgmtRepository.save(user);
	}
	
	public List<User> login(String email,String password){
		return this.userQuery.login(email, password);	
	}
	public Organization orgLogin(String email,String password){
		return this.userQuery.orgLogin(email, password);	
	}
	public User getByUserName(String userName){
		return this.userMgmtRepository.findByEmail(userName);
	}
	
	public User getByEmail(String email){
		return this.userMgmtRepository.findByEmail(email);
	}
	public Organization getByName(String name){
		return this.organizationRepository.findByName(name);
	}
	public Organization getBySite(String site){
		return this.organizationRepository.findBySite(site);
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
	
	public List<UserWithRole> getUserList(int projectId,int orgId){
		return this.userQuery.getUserList(projectId,orgId);
	}
	
	public List<UserWithRole> searchUser(String name,int roleId){
		return this.userQuery.searchUser(name,roleId);
	}
	public List<UserDetail> getUserDetail(int id){
		return this.userQuery.getUserDetail(id);
	}
	
	public UserRoleMapping saveUserRole(UserRoleMapping userRole){
		return this.userRoleMappingRepository.save(userRole);
	}
	public UserRoleMapping getRoleByUserId(int id){
		return this.userRoleMappingRepository.findByUserId(id);
	}
	public List<UserDetail> getUserByPagination(int from,int to,String email,String name,int orgId){
		return this.userQuery.getPaginationForUser(from, to,email,name,orgId);
	}
	public List<UserDetail> searchUserDetail(String name,String mailId,String phoneNumber,String role,String active,int orgId){
		return this.userQuery.searchUserDetail(name, mailId, phoneNumber, role,active,orgId);
	}
	public List<UserDetail> getActiveUser(String value){
		return this.userQuery.getActiveUser(value);
	}
	public List<UserDetail> getUserByProjectId(int projectId){
		return this.userQuery.getUserByProjectId(projectId);
	}
	public Long getCount(){
		return this.userMgmtRepository.count();
	}
	
	public List<UserRole> getRoles(){
		return this.userRoleRepository.findAll();
	}
	public Organization getOrgName(int orgId){
		return this.organizationRepository.findById(orgId);
	}
	
	public User getUserProfileToken(String token) {
		return this.userMgmtRepository.findBySecureToken(token);
	}
	public List<User> getUserActive(String token) {
		return this.userQuery.findSecureToken(token);
	}
	public Organization orgLogo(String token){
		return this.userQuery.orgLogo(token);	
	}
}

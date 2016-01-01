package me.king.wx.qy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.king.wx.qy.department.api.DepartmentApi;
import me.king.wx.qy.department.vo.Department;
import me.king.wx.qy.user.api.UserApi;
import me.king.wx.qy.user.vo.User;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		AddressBookManager.java    
 * @Description:    通讯录管理  
 * @author: 		WangBuEr
 * @date:   		2017年3月30日 下午6:37:53   
 * @version 		V1.0
 */
public class AddressBookManager {
	/**
	 * 同步通讯录
	 * @param token 令牌
	 * @param appDeptList 应用组织列表
	 * @param appUserList 应用用户列表
	 */
	public static void sync(AccessToken token,List<Department> appDeptList,List<User> appUserList){
		List<Department> wxDeptList = DepartmentApi.getDeptList(token,"1");
		List<User> wxUserList = UserApi.getDeptUsers(token, "1", true, 0);
		deleteMany(token, appDeptList, appUserList, wxDeptList, wxUserList);
		List<Department> updateDepartmentList =  getUpdate(wxDeptList,appDeptList);
		List<User> updateUserList = getUpdate(wxUserList,appUserList);
		List<Department> deptTree = buildDeptLevel(appDeptList);
		for(Department dept : deptTree){
			saveOrUpdateDept(token,dept,updateDepartmentList);
		}
		//同步用户
		for(User user : appUserList){
			if(updateUserList.contains(user)){
				UserApi.updateUser(token, user);
			}else{
				UserApi.addUser(token, user);
			}
		}
	}
	/**
	 * 保存或更新部门
	 * @param token 令牌
	 * @param dept 部门
	 * @param updateDepartmentList 需要更新的部门列表
	 */
	private static void saveOrUpdateDept(AccessToken token, Department dept, List<Department> updateDepartmentList) {
		if(updateDepartmentList.contains(dept)){
			DepartmentApi.updateDept(token, dept);
		}else{
			DepartmentApi.addDept(token, dept);
		}
		List<Department> subDeptList = dept.getSubDeptList();
		if(subDeptList != null && !subDeptList.isEmpty()){
			for(Department subDept : subDeptList){
				saveOrUpdateDept(token, subDept, updateDepartmentList);
			}
		}
		
	}
	/**
	 * 构建部门层级列表
	 * @param deptList 部门列表
	 * @return 部门层级列表
	 */
	private static List<Department> buildDeptLevel(List<Department> deptList){
		//获取根部门
		List<Department> rootDeptList = getRootDepartment(deptList);
		deptList.removeAll(rootDeptList);
		for(Department dept : rootDeptList){
			dept.setParentid("1");
			buildSubDept(dept,deptList);
		}
		return rootDeptList;
	}
	/**
	 * 构建子部门
	 * @param parentDept 父部门
	 * @param deptList 部门列表
	 */
	private static void buildSubDept(Department parentDept, List<Department> deptList) {
		List<Department> subDeptList = findSubDept(parentDept,deptList);
		if(!subDeptList.isEmpty()){
			for(Department dept : subDeptList){
				List<Department> childrenDeptList = parentDept.getSubDeptList();
				if(childrenDeptList == null){
					childrenDeptList = new ArrayList<Department>();
					parentDept.setSubDeptList(childrenDeptList);
				}
				childrenDeptList.add(dept);
				buildSubDept(dept, deptList);
			}
		}
	}
	/**
	 * 查找子部门
	 * @param parentDept 父部们
	 * @param deptList 部门列表
	 * @return 子部门列表
	 */
	private static List<Department> findSubDept(Department parentDept, List<Department> deptList) {
		List<Department> result = new ArrayList<Department>();
		for(Department dept : deptList){
			if(dept.getParentid().equals(parentDept.getId())){
				result.add(dept);
			}
		}
		return result;
	}

	/**
	 * 获取根部门
	 * @param deptList 部门列表
	 * @return 根部门列表
	 */
	private static List<Department> getRootDepartment(List<Department> deptList) {
		List<Department> result = new ArrayList<Department>();
		for(Department dept : deptList){
			if(dept.getParentid()== null || dept.getParentid().equals("")){
				result.add(dept);
			}
		}
		return result;
	}

	/**
	 * 删除微信企业号中多余的部门和用户
	 * @param token
	 * @param appDeptList
	 * @param appUserList
	 * @param wxDeptList
	 * @param wxUserList
	 */
	private static void deleteMany(AccessToken token, List<Department> appDeptList, List<User> appUserList,
			List<Department> wxDeptList, List<User> wxUserList) {
		//微信企业号中多的部门列表
		List<Department> wxManyDeptList = getMany(wxDeptList,appDeptList);
		//微信企业号中多的用户列表
		List<User> wxManyUserList = getMany(wxUserList, appUserList);
		//删除多余的微信企业号中的用户
		for(User user : wxManyUserList){
			UserApi.deleteUser(token, user.getUserid());
		}
		//删除多余的微信企业号中的部门
		for(Department dept : wxManyDeptList){
			DepartmentApi.deleteDept(token, dept.getId());
		}
	}
	/**
	 * 获取多余的数据
	 * @param wxList 微信企业号数据
	 * @param appList app端数据
	 * @return 多余的数据
	 */
	private static <T> List<T> getMany(List<T> wxList,List<T> appList){
		List<T> result = new ArrayList<T>();
		Collections.copy(result, wxList);
		result.removeAll(appList);
		return result;
	}
	/**
	 * 获取需要更新的数据
	 * @param wxList 微信端数据
	 * @param appList app端数据
	 * @return 需要更新的数据
	 */
	private static <T> List<T> getUpdate(List<T> wxList,List<T> appList) {
		List<T> result = new ArrayList<T>();
		Collections.copy(result, wxList);
		result.retainAll(appList);
		return result;
	}
}

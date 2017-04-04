package me.king.wx.qy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOG = LoggerFactory.getLogger(AddressBookManager.class);
	/**
	 * 同步成功新增部门数
	 */
	public static final String SYNC_SUCCESS_ADD_DEPT = "syncSuccessAddDept";
	/**
	 * 同步成功更新部门数
	 */
	public static final String SYNC_SUCCESS_UPDATE_DEPT = "syncSuccessUpdateDept";
	/**
	 * 同步成功新增用户
	 */
	public static final String SYNC_SUCCESS_ADD_USER = "syncSuccessAddUser";
	/**
	 * 同步成功更新用户
	 */
	public static final String SYNC_SUCCESS_UPDATE_USER = "syncSuccessUpdateUser";
	/**
	 * 同步失败的部门
	 */
	public static final String SYNC_FAIL_DEPT = "syncFailDept";
	/**
	 * 同步失败的用户
	 */
	public static final String SYNC_FIAL_USER = "syncFailUser";
	/**
	 * 跳过同步的用户
	 */
	public static final String SKIP_SYNC_USER = "skipSyncUser";
	/**
	 * 同步通讯录
	 * @param token 令牌
	 * @param appDeptList 应用组织列表
	 * @param appUserList 应用用户列表
	 * @return 同步情况
	 */
	public static Map<String, Integer> sync(AccessToken token,List<Department> appDeptList,List<User> appUserList){
		Map<String, Integer> result = new HashMap<String, Integer>();
		//微信企业号中的部门列表
		List<Department> wxDeptList = DepartmentApi.getDeptList(token,"1");
		//移除微信企业号中的根部门
		wxDeptList.remove(new Department("1"));
		//微信企业号中的用户列表
		List<User> wxUserList = UserApi.getDeptUsers(token, "1", true, 0);
		//需要删除的部门列表
		List<Department> delDeptList = getMany(wxDeptList,appDeptList);
		//需要更新的用户列表
		List<User> delUserList = getMany(wxUserList, appUserList);
		//需要更新的部门列表
		List<Department> updateDepartmentList =  getUpdate(wxDeptList,appDeptList);
		//需要更新的用户列表
		List<User> updateUserList = getUpdate(wxUserList,appUserList);
		//部门树
		List<Department> deptTree = buildDeptLevel(appDeptList);
		//同步部门
		for(Department dept : deptTree){
			result.putAll(syncDept(token,dept,updateDepartmentList));
		}
		//同步用户
		result.putAll(syncUser(token, appUserList, updateUserList));
		//删除微信企业号中多余的部门和用户
		deleteWxMany(token,delDeptList,delUserList);
		LOG.debug("同步通讯录情况：{}",result);
		return result;
	}
	/**
	 * 同步用户
	 * @param token 令牌
	 * @param appUserList 业务端用户列表
	 * @param updateUserList 需要更新的用户列表
	 * @return 同步情况
	 */
	private static Map<String, Integer> syncUser(AccessToken token, List<User> appUserList, List<User> updateUserList) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		int successAddUser = 0;
		int successUpdateUser = 0;
		int fialSyncUser = 0;
		int skipSyncUser = 0;
		for(User user : appUserList){
			if(updateUserList.contains(user)){
				if(UserApi.updateUser(token, user)){
					successUpdateUser++;
				}else{
					fialSyncUser++;
				}
			}else{
				if(user.getEmail() == null
						&& user.getMobile() == null
						&& user.getWeixinid() == null){
					skipSyncUser++;
				}else{
					if(UserApi.addUser(token, user)){
						successAddUser++;
					}else{
						fialSyncUser++;
					}
				}
			}
		}
		result.put(AddressBookManager.SYNC_SUCCESS_ADD_USER, successAddUser);
		result.put(AddressBookManager.SYNC_SUCCESS_UPDATE_USER, successUpdateUser);
		result.put(AddressBookManager.SYNC_FIAL_USER, fialSyncUser);
		result.put(AddressBookManager.SKIP_SYNC_USER, skipSyncUser);
		return result;
	}
	/**
	 * 删除微信端多余的部门和用户
	 * @param token 令牌
	 * @param delDeptList 部门列表
	 * @param delUserList 用户列表
	 */
	private static void deleteWxMany(AccessToken token,List<Department> delDeptList, List<User> delUserList) {
		for(User user : delUserList){
			UserApi.deleteUser(token, user.getUserid());
		}
		List<Department> deptTree = buildDeptLevel(delDeptList);
		for(Department dept : deptTree){
			deleteDept(token, dept);
		}
	}
	/**
	 * 删除部门
	 * @param token 令牌
	 * @param dept 部门
	 * @return 是否删除成功
	 */
	private static boolean deleteDept(AccessToken token,Department dept){
		List<Department> subDeptList = dept.getSubDeptList();
		if(subDeptList != null && !subDeptList.isEmpty()){
			for(Department subDept : subDeptList){
				deleteDept(token,subDept);
			}
		}else{
			DepartmentApi.deleteDept(token, dept.getId());
		}
		return true;
	}
	/**
	 * 保存或更新部门
	 * @param token 令牌
	 * @param dept 部门
	 * @param updateDepartmentList 需要更新的部门列表
	 * @return 同步情况
	 */
	private static Map<String, Integer> syncDept(AccessToken token, Department dept, List<Department> updateDepartmentList) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		int successAddDept = 0;
		int successUpdateDept = 0;
		int failSyncDept = 0;
		if(updateDepartmentList.contains(dept)){
			if(DepartmentApi.updateDept(token, dept)){
				successUpdateDept++;
			}else{
				failSyncDept++;
			}
		}else{
			if(DepartmentApi.addDept(token, dept)){
				successUpdateDept++;
			}else{
				failSyncDept++;
			}
		}
		List<Department> subDeptList = dept.getSubDeptList();
		if(subDeptList != null && !subDeptList.isEmpty()){
			for(Department subDept : subDeptList){
				syncDept(token, subDept, updateDepartmentList);
			}
		}
		result.put(AddressBookManager.SYNC_SUCCESS_ADD_DEPT, successAddDept);
		result.put(AddressBookManager.SYNC_SUCCESS_UPDATE_DEPT, successUpdateDept);
		result.put(AddressBookManager.SYNC_FAIL_DEPT, failSyncDept);
		return result;
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
			if(dept.getParentid()== null || dept.getParentid().equals("")
					|| !deptList.contains(new Department(dept.getParentid()))){
				result.add(dept);
			}
		}
		return result;
	}

	/**
	 * 获取多余的数据
	 * @param wxList 微信企业号数据
	 * @param appList app端数据
	 * @return 多余的数据
	 */
	private static <T> List<T> getMany(List<T> wxList,List<T> appList){
		List<T> result = new ArrayList<T>(appList.size());
		result.addAll(wxList);
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
		List<T> result = new ArrayList<T>(appList.size());
		result.addAll(wxList);
		result.retainAll(appList);
		return result;
	}
}

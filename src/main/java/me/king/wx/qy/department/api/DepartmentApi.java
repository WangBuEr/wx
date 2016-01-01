package me.king.wx.qy.department.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.king.wx.http.HttpClientUtil;
import me.king.wx.qy.department.vo.Department;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		DepartmentApi.java    
 * @Description:    部门api
 * @author: 		WangBuEr
 * @date:   		2017年3月17日 下午2:45:49   
 * @version 		V1.0
 */
public class DepartmentApi {
	private static final Logger LOG = LoggerFactory.getLogger(DepartmentApi.class);
	/**
	 * 获取部门列表URL
	 */
	private static final String GET_DEPT_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
	/**
	 * 删除部门URL
	 */
	private static final String DEL_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete";
	/**
	 * 增加部门URL
	 */
	private static final String ADD_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=";
	/**
	 * 更新部门URL
	 */
	private static final String UPDATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=";
	/**
	 * 获取部门列表
	 * @param token 令牌
	 * @param pid 父部门id,可为null,如果为null则获取所有部门列表
	 * @return 部门列表
	 */
	public static List<Department> getDeptList(final AccessToken token,final String pid){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("id", pid);
		String getDeptListResult = HttpClientUtil.getServiceResponseAsString(GET_DEPT_LIST_URL, params);
		if(getDeptListResult != null){
			JSONObject getDeptListJsonObj = JSONObject.parseObject(getDeptListResult);
			int errorCode = getDeptListJsonObj.getIntValue("errcode");
			if(0 == errorCode){
				String deptListJson = getDeptListJsonObj.getString("department");
				List<Department> result = JSONObject.parseArray(deptListJson, Department.class);
				LOG.debug("获取部门列表成功:{}",result);
				return result;
			}else{
				LOG.error("获取部门列表失败:{}",getDeptListResult);
				return Collections.emptyList();
			}
		}else{
			LOG.error("获取部门列表失败:请求服务失败");
			return Collections.emptyList();
		}
	}
	/**
	 * 增加部门
	 * @param token 令牌
	 * @param dept 部门
	 * @return 是否增加成功
	 */
	public static Boolean addDept(final AccessToken token,final Department dept){
		String addDeptResult = HttpClientUtil.postJsonServiceResponseAsString(
				DepartmentApi.ADD_DEPT_URL + token.getToken(), JSONObject.toJSONString(dept), Charsets.UTF_8.name());
		if(addDeptResult != null){
			if(JSONObject.parseObject(addDeptResult).getInteger("errcode") == 0){
				LOG.debug("创建部门成功");
				return true;
			}else{
				LOG.error("创建部门失败:{}",addDeptResult);
				return false;
			}
		}else{
			LOG.error("创建部门失败:请求服务失败");
			return false;
		}
	}

	
	/**
	 * 更新部门
	 * @param token 令牌
	 * @param dept 需要更新的部门信息
	 * @return 是否成功
	 */
	public static Boolean updateDept(final AccessToken token,final Department dept){
		String addDeptResult = HttpClientUtil.postJsonServiceResponseAsString(
				DepartmentApi.UPDATE_DEPT_URL + token.getToken(), JSONObject.toJSONString(dept), Charsets.UTF_8.name());
		if(addDeptResult != null){
			if(JSONObject.parseObject(addDeptResult).getInteger("errcode") == 0){
				LOG.debug("更新部门成功");
				return true;
			}else{
				LOG.error("更新部门失败:{}",addDeptResult);
				return false;
			}
		}else{
			LOG.error("更新部门失败:请求服务失败");
			return false;
		}
	}
	/**
	 * 删除部门
	 * @param token 令牌
	 * @param deptId 需要删除的部门id
	 * @return 是否成功
	 */
	public static Boolean deleteDept(final AccessToken token,final String deptId){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("id", deptId);
		String delDeptResult = HttpClientUtil.getServiceResponseAsString(DepartmentApi.DEL_DEPT_URL, params);
		if(delDeptResult != null){
			if(JSONObject.parseObject(delDeptResult).getInteger("errcode") == 0){
				LOG.debug("删除部门成功");
				return true;
			}else{
				LOG.error("删除部门失败:{}",delDeptResult);
				return false;
			}
		}else{
			LOG.error("删除部门失败:请求服务失败");
			return false;
		}
		
	}
}

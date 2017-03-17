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
	private static final String GET_DEPT_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
	private static final String DEL_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete";
	private static final String ADD_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=";
	/**
	 * 同步部门,会和企业号中的现有部门进行比较,交集部分更新,
	 * 企业号中的差集删除,需要同步的差集新增
	 * @param needDeptList 需要同步的部门列表
	 */
	public static void sync(List<Department> needDeptList){
		
	}
	
	public static List<Department> getDeptList(final AccessToken token,final String pid){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("id", pid);
		String getDeptListJson = HttpClientUtil.getServiceResponseAsString(GET_DEPT_LIST_URL, params);
		if(getDeptListJson != null){
			JSONObject getDeptListJsonObj = JSONObject.parseObject(getDeptListJson);
			int errorCode = getDeptListJsonObj.getIntValue("errcode");
			if(0 == errorCode){
				String deptListJson = getDeptListJsonObj.getString("department");
				List<Department> result = JSONObject.parseArray(deptListJson, Department.class);
				LOG.debug("获取部门列表成功:{}",result);
				return result;
			}else{
				LOG.error("获取部门列表失败:{}",getDeptListJson);
				return Collections.emptyList();
			}
		}else{
			LOG.error("获取部门列表失败:请求服务失败");
			return Collections.emptyList();
		}
	}
	
	public static Boolean addDept(AccessToken token,Department dept){
		Map<String, String> params = new HashMap<String, String>(5);
		params.put("access_token", token.getToken());
		params.put("name", dept.getName());
		params.put("parentid", dept.getParentid());
		params.put("order", dept.getOrder());
		params.put("id", dept.getId());
		String addDeptResult = HttpClientUtil.postJsonServiceResponseAsString(
				DepartmentApi.ADD_DEPT_URL + token.getToken(), JSONObject.toJSONString(params), Charsets.UTF_8.name());
		if(addDeptResult != null){
			
		}
		return false;
	}
	
	public static Boolean updateDept(AccessToken token,Department dept){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		return false;
	}
	
	public static Boolean deleteDept(AccessToken token,String deptId){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("id", deptId);
		String delDeptJson = HttpClientUtil.getServiceResponseAsString(DEL_DEPT_URL, params);
		if(delDeptJson != null){
			if(JSONObject.parseObject(delDeptJson).getInteger("errcode") == 0){
				LOG.debug("删除部门成功");
				return true;
			}else{
				LOG.error("删除部门失败:{}",delDeptJson);
				return false;
			}
		}else{
			LOG.error("删除部门失败:请求服务失败");
			return false;
		}
		
	}
	
}

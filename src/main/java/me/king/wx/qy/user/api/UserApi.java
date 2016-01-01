package me.king.wx.qy.user.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.king.wx.http.HttpClientUtil;
import me.king.wx.qy.user.vo.User;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		UserApi.java    
 * @Description:    用户api
 * @author: 		WangBuEr
 * @date:   		2017年3月30日 上午11:24:54   
 * @version 		V1.0
 */
public class UserApi {
	private static final Logger LOG = LoggerFactory.getLogger(UserApi.class);
	/**
	 * 增加用户URL
	 */
	private static final String ADD_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=";
	/**
	 * 更新用户URL
	 */
	private static final String UPTDATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=";
	/**
	 * 删除用户URL
	 */
	private static final String DELETE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete";
	/**
	 * 批量删除用户URL
	 */
	private static final String BATCH_DELETE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=";
	/**
	 * 获取部门成员列表URL
	 */
	private static final String GET_DEPT_USERS_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
	/**
	 * 增加用户
	 * @param token 令牌
	 * @param user 用户信息
	 * @return 是否增加成功
	 */
	public static boolean addUser(final AccessToken token,final User user){
		String addUserResult = HttpClientUtil.postJsonServiceResponseAsString(UserApi.ADD_USER_URL + token.getToken(),
				JSONObject.toJSONString(user), Charsets.UTF_8.name());
		if(addUserResult != null){
			if(JSONObject.parseObject(addUserResult).getInteger("errcode") == 0){
				LOG.debug("创建用户成功");
				return true;
			}else{
				LOG.error("创建用户失败:{}",addUserResult);
				return false;
			}
		}else{
			LOG.error("创建用户失败:请求服务失败");
			return false;
		}
	}
	/**
	 * 更新用户
	 * @param token 令牌
	 * @param user 用户信息
	 * @return 是否更新成功
	 */
	public static boolean updateUser(final AccessToken token,final User user){
		String updateUserResult = HttpClientUtil.postJsonServiceResponseAsString(UserApi.UPTDATE_USER_URL + token.getToken(),
				JSONObject.toJSONString(user), Charsets.UTF_8.name());
		if(updateUserResult != null){
			if(JSONObject.parseObject(updateUserResult).getInteger("errcode") == 0){
				LOG.debug("更新用户成功");
				return true;
			}else{
				LOG.error("更新用户失败:{}",updateUserResult);
				return false;
			}
		}else{
			LOG.error("更新用户失败:请求服务失败");
			return false;
		}
	}
	/**
	 * 删除用户
	 * @param token 令牌
	 * @param userid 成员UserID。对应管理端的帐号
	 * @return
	 */
	public static boolean deleteUser(final AccessToken token,final String userid){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("userid", userid);
		String delUserResult = HttpClientUtil.getServiceResponseAsString(UserApi.DELETE_USER_URL, params);
		if(delUserResult != null){
			if(JSONObject.parseObject(delUserResult).getInteger("errcode") == 0){
				LOG.debug("删除用户成功");
				return true;
			}else{
				LOG.error("删除用户失败:{}",delUserResult);
				return false;
			}
		}else{
			LOG.error("删除用户失败:请求服务失败");
			return false;
		}
	}
	/**
	 * 批量删除用户
	 * @param token 
	 * @param userIds 用户id列表
	 * @return
	 */
	public static boolean batchDeleteUser(final AccessToken token,final List<String> userIds){
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("useridlist", JSONObject.toJSONString(userIds));
		String batchDelResult = HttpClientUtil.postJsonServiceResponseAsString(
				UserApi.BATCH_DELETE_USER_URL + token.getToken(), JSONObject.toJSONString(params),
				Charsets.UTF_8.name());
		if(batchDelResult != null){
			if(JSONObject.parseObject(batchDelResult).getInteger("errcode") == 0){
				LOG.debug("批量删除用户成功");
				return true;
			}else{
				LOG.error("批量删除用户失败:{}",batchDelResult);
				return false;
			}
		}else{
			LOG.error("批量删除用户失败:请求服务失败");
			return false;
		}
	}
	/**
	 * 获取部门成员
	 * @param token 令牌
	 * @param deptId 获取的部门id
	 * @param fetchChild 1/0：是否递归获取子部门下面的成员
	 * @param status 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 * @return 部门成员列表
	 */
	public static List<User> getDeptUsers(AccessToken token,String deptId,boolean fetchChild,Integer status){
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("access_token", token.getToken());
		params.put("department_id", deptId);
		params.put("fetch_child", fetchChild?"1":"0");
		if(status != null){
			params.put("status", String.valueOf(status));
		}else{
			params.put("status", "4");
		}
		String getDeptUsersResult = HttpClientUtil.getServiceResponseAsString(UserApi.GET_DEPT_USERS_URL,params);
		if(getDeptUsersResult != null){
			JSONObject getDeptUsersJsonObj = JSONObject.parseObject(getDeptUsersResult);
			int errorCode = getDeptUsersJsonObj.getIntValue("errcode");
			if(errorCode == 0){
				String userListJson = getDeptUsersJsonObj.getString("userlist");
				List<User> result = JSONObject.parseArray(userListJson, User.class);
				LOG.error("获取部门成员成功:{}",result);
				return result;
			}else{
				LOG.error("获取部门成员失败:{}",getDeptUsersResult);
				return Collections.emptyList();
			}
		}else{
			LOG.error("获取部门成员失败:请求服务失败");
			return Collections.emptyList();
		}
	}
}

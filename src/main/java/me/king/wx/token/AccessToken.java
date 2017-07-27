package me.king.wx.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.king.wx.http.HttpClientUtil;

/**
 * 微信AccessToken
 * @author BuEr
 *
 */
public class AccessToken {
	private static final Logger LOG = LoggerFactory.getLogger(AccessToken.class);
	private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	/**
	 * token
	 */
	private String token;
	/**
	 * token有效时间
	 */
	private long expires;         
	/**
	 * token产生时间
	 */
	private long tokenTime;   
	/**
	 * 冗余时间，提前10秒就去请求新的token
	 */
	private int redundance = 10*1000; 
	private AccessToken(){}
	private AccessToken(String token, long expires, long tokenTime) {
		super();
		this.token = token;
		this.expires = expires;
		this.tokenTime = tokenTime;
	}

	/**
	 * 得到access token
	 * @param corpid 企业Id
	 * @param corpsecret 管理组的凭证密钥
	 * @return AccessToken
	 */
	public static final AccessToken getAccessToken(String corpid,String corpsecret){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("corpid", corpid);
		params.put("corpsecret", corpsecret);
		String tokenJson = HttpClientUtil.getServiceResponseAsString(AccessToken.ACCESS_TOKEN_URL, params);
		if(tokenJson != null && !"".equals(tokenJson)){
			JSONObject jsonObj = JSONObject.parseObject(tokenJson);
			if(jsonObj.containsKey("access_token") &&  jsonObj.getString("access_token") != null){
				String token = jsonObj.getString("access_token");
				long expires = jsonObj.getLongValue("expires_in");
				AccessToken result = new AccessToken(token, expires, System.currentTimeMillis());
				LOG.debug("获取微信AccessToken成功:{}",result);
				return result;
			}else{
				LOG.error("获取微信AccessToken失败:{}", tokenJson);
				return null;
			}
		}else{
			LOG.error("获取微信AccessToken失败:服务请求失败");
			return null;
		}
	}
	
	/**
	 * 得到有效时间
	 */
	public long getExpires() {
		return expires;
	}
	
	
	/**
	 * accessToken 是否有效
	 * @return true:有效，false: 无效
	 */
	public boolean isValid(){
		//黑名单判定法
		if(token == null)
			return false;
		if(this.expires <= 0)
			return false;
		//过期
		if(isExpire())
			return false;
		return true;
	}
	
	public String getToken() {
		return token;
	}
	/**
	 * 是否过期
	 * @return true 过期 false：有效
	 */
	private boolean isExpire(){
		Date currentDate = new Date();
		long currentTime = currentDate.getTime();
		long expiresTime = expires * 1000 - redundance;
		//判断是否过期
		if((tokenTime + expiresTime) > currentTime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AccessToken [token=" + token + ", expires=" + expires + ", tokenTime=" + tokenTime + "]";
	}
	
	
}

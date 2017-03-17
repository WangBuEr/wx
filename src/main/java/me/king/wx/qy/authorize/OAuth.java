package me.king.wx.qy.authorize;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.king.wx.http.HttpClientUtil;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		OAuth.java    
 * @Description:    OAuth2.0验证
 * @author: 		WangBuEr
 * @date:   		2017年3月17日 下午2:09:27   
 * @version 		V1.0
 */
public class OAuth {
	private static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
	private static final String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
	private static final Logger LOG = LoggerFactory.getLogger(OAuth.class);
	public static String structureAuthUrl(final String redirectUri,final String appid){
		try {
			String result = AUTH_URL + "appid="+appid+"&redirect_uri="+URLEncoder.encode(redirectUri, Charsets.UTF_8.name())+"&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
			LOG.debug("构造企业获取code链接成功:{}",result);
			return result;
		} catch (UnsupportedEncodingException e) {
			LOG.error("构造企业获取code链接失败:{}",e);
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getUserId(final String code,final AccessToken token){
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("access_token", token.getToken());
		params.put("code", code);
		String userJson = HttpClientUtil.getServiceResponseAsString(GET_USER_URL, params);
		if(userJson != null){
			String result = JSONObject.parseObject(userJson).getString("UserId");
			if(result != null){
				return result;
			}else{
				LOG.error("获取用户id失败:{}",userJson);
				return null;
			}
		}else{
			LOG.error("获取用户id失败:请求服务失败");
			return null;
		}
	}
}

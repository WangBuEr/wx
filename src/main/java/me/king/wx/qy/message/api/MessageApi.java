package me.king.wx.qy.message.api;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import me.king.wx.http.HttpClientUtil;
import me.king.wx.qy.message.vo.NewsMessage;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		MessageApi.java    
 * @Description:    消息服务
 * @author: 		WangBuEr
 * @date:   		2017年4月18日 上午4:22:50   
 * @version 		V1.0
 */
public class MessageApi {
	private static final Logger LOG = LoggerFactory.getLogger(MessageApi.class);
	private static final String URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	public static void sendNews(AccessToken token,NewsMessage msg){
		LOG.debug(JSONObject.toJSONString(msg));
		String sendMsgJsonResult = HttpClientUtil.postJsonServiceResponseAsString(URL + token.getToken(), JSONObject.toJSONString(msg),
				Charsets.UTF_8.name());
		LOG.debug("发送news消息结果:{}",sendMsgJsonResult);
	}
}

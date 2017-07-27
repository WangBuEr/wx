package me.king.wx.qy.agent.vo;

import me.king.wx.token.AccessToken;

/**  
 * @Title:  		Agent.java    
 * @Description:    应用 
 * @author: 		WangBuEr
 * @date:   		2017年7月26日 下午1:51:59   
 * @version 		V1.0
 */
public class Agent {
	/**
	 * 
	 */
	private String id;
	/**
	 * 应用名称
	 */
	private String name;
	/**
	 * 应用logo
	 */
	private String logoUrl;
	/**
	 * 应用secret
	 */
	private String secret;
	/**
	 * 当前应用token
	 */
	private AccessToken token;
	public Agent() {
		super();
	}
	
	public Agent(String id, String secret) {
		super();
		this.id = id;
		this.secret = secret;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public AccessToken getToken() {
		return token;
	}
	public void setToken(AccessToken token) {
		this.token = token;
	}
	
}

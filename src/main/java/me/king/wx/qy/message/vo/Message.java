package me.king.wx.qy.message.vo;

/**  
 * @Title:  		Message.java    
 * @Description:    消息
 * @author: 		WangBuEr
 * @date:   		2017年4月18日 上午4:22:59   
 * @version 		V1.0
 */
public class Message {
	/**
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	 */
	protected String touser;
	/**
	 * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	protected String toparty;
	/**
	 * 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	protected String totag;
	/**
	 * 企业应用的id，整型。可在应用的设置页面查看
	 */
	protected String agentid;
	
	public Message() {
		super();
	}
	public Message(String touser, String toparty, String totag, String agentid) {
		super();
		this.touser = touser;
		this.toparty = toparty;
		this.totag = totag;
		this.agentid = agentid;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
}

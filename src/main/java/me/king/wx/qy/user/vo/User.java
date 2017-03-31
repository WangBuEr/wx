package me.king.wx.qy.user.vo;

import java.util.List;

/**  
 * @Title:  		User.java    
 * @Description:    用户信息
 * @author: 		WangBuEr
 * @date:   		2017年3月20日 上午10:31:18   
 * @version 		V1.0
 */
public class User {
	/**
	 * 成员UserID。对应管理端的帐号
	 */
	private String userid;
	/**
	 * 成员名称
	 */
	private String name;
	/**
	 * 成员所属部门id列表
	 */
	List<String> department;
	/**
	 * 职位信息
	 */
	private String position;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 性别。0表示未定义，1表示男性，2表示女性
	 */
	private String gender;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * weixinid
	 */
	private String weixinid;
	/**
	 * 头像url。注：如果要获取小图将url最后的"/0"改成"/64"即可
	 */
	private String avatar;
	/**
	 * 关注状态: 1=已关注，2=已禁用，4=未关注
	 */
	private String status;
	/**
	 * 扩展属性
	 */
	private Extattr extattr;
	
	public User() {
		super();
	}
	public User(String userid, String name, List<String> department, String mobile, String gender, String weixinid) {
		super();
		this.userid = userid;
		this.name = name;
		this.department = department;
		this.mobile = mobile;
		this.gender = gender;
		this.weixinid = weixinid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getDepartment() {
		return department;
	}
	public void setDepartment(List<String> department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Extattr getExtattr() {
		return extattr;
	}
	public void setExtattr(Extattr extattr) {
		this.extattr = extattr;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [userId=" + userid + ", name=" + name + ", department=" + department + ", position=" + position
				+ ", mobile=" + mobile + ", gender=" + gender + ", email=" + email + ", weixinid=" + weixinid
				+ ", avatar=" + avatar + ", status=" + status + ", extattr=" + extattr + "]";
	}
}

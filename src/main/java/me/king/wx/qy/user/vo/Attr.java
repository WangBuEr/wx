package me.king.wx.qy.user.vo;

/**  
 * @Title:  		Attrs.java    
 * @Description:    属性
 * @author: 		WangBuEr
 * @date:   		2017年3月20日 上午10:46:12   
 * @version 		V1.0
 */
public class Attr {
	/**
	 * 属性名称
	 */
	private String name;
	/**
	 * 属性值
	 */
	private String value;
	
	public Attr() {
		super();
	}
	
	public Attr(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Attr [name=" + name + ", value=" + value + "]";
	}
	
}


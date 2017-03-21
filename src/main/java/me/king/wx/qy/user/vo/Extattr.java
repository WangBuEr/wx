package me.king.wx.qy.user.vo;

import java.util.List;

/**  
 * @Title:  		Extattr.java    
 * @Description:    扩展属性  
 * @author: 		WangBuEr
 * @date:   		2017年3月20日 上午10:45:56   
 * @version 		V1.0
 */
public class Extattr {
	/**
	 * 属性列表
	 */
	private List<Attr> attrs;

	public List<Attr> getAttrs() {
		return attrs;
	}
	
	public Extattr() {
		super();
	}

	public Extattr(List<Attr> attrs) {
		super();
		this.attrs = attrs;
	}

	public void setAttrs(List<Attr> attrs) {
		this.attrs = attrs;
	}

	@Override
	public String toString() {
		return "Extattr [attrs=" + attrs + "]";
	}
	
}

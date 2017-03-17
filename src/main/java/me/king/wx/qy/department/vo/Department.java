package me.king.wx.qy.department.vo;

/**
 * @Title: Department.java
 * @Description: 部门
 * @author: WangBuEr
 * @date: 2017年3月17日 下午2:42:32
 * @version V1.0
 */
public class Department {
	/**
	 * id
	 */
	private String id;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 父部门
	 */
	private String parentid;
	/**
	 * 排序
	 */
	private String order;
	
	public Department() {
		super();
	}
	
	public Department(String id, String name, String parentid, String order) {
		super();
		this.id = id;
		this.name = name;
		this.parentid = parentid;
		this.order = order;
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

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Department{" + "id=" + id + ", name='" + name + '\'' + ", parentId=" + parentid + ", order=" + order
				+ '}';
	}
}

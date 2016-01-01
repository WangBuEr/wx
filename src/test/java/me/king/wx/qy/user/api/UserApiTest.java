package me.king.wx.qy.user.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import me.king.wx.qy.user.vo.User;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		UserApiTest.java    
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 		KELAILE
 * @date:   		2017年3月30日 下午6:02:38   
 * @version 		V1.0
 */
public class UserApiTest {

	@Test
	public void testAddUser() {
		AccessToken token = AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o");
		User u = new User();
		List<String> deptList = new ArrayList<String>(1);
		deptList.add("33");
		u.setDepartment(deptList);
		u.setUserid("zhangsan");
		u.setWeixinid("Wang_Jian_Min_Sun");
		u.setName("test");
		UserApi.addUser(token, u);
		
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchDeleteUser() {
		fail("Not yet implemented");
	}

}

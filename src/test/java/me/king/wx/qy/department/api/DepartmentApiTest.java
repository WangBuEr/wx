package me.king.wx.qy.department.api;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import me.king.wx.token.AccessToken;

/**  
 * @Title:  		DepartmentApiTest.java    
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 		KELAILE
 * @date:   		2017年3月17日 下午3:27:38   
 * @version 		V1.0
 */
public class DepartmentApiTest {

	@Test
	public void testSync() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeptList() {
		AccessToken token = AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o");
		assertNotEquals(DepartmentApi.getDeptList(token, null), Collections.emptyList());
	}

	@Test
	public void testAddDept() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateDept() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteDept() {
		AccessToken token = AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o");
		assertEquals(DepartmentApi.deleteDept(token, "5"), true);
	}

}

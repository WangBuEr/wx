package me.king.wx.qy;

import java.util.List;

import org.junit.Test;

import me.king.wx.qy.department.vo.Department;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		AddressBookManagerTest.java    
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 		KELAILE
 * @date:   		2017年3月31日 上午10:56:34   
 * @version 		V1.0
 */
public class AddressBookManagerTest {

	@Test
	public void test() {
		AccessToken token = AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o");
		AddressBookManager.sync(token, null, null);
	}
	
	

}

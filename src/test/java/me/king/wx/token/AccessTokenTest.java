package me.king.wx.token;

import static org.junit.Assert.*;

import org.junit.Test;


public class AccessTokenTest {

	@Test
	public void testGetToken() {
		assertNotNull(AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o"));
	}

}

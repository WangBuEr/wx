package me.king.wx.qy.message.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import me.king.wx.qy.message.vo.NewsArticles;
import me.king.wx.qy.message.vo.NewsMessage;
import me.king.wx.token.AccessToken;

/**  
 * @Title:  		MessageApiTest.java    
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 		KELAILE
 * @date:   		2017年4月18日 上午4:47:47   
 * @version 		V1.0
 */
public class MessageApiTest {

	@Test
	public void test() {
		AccessToken token = AccessToken.getAccessToken("wx025aa188673d0d16", "SP0mzO3i9BZlx0WYlZ66d5ONMFC_mzdYdNEd6_DVkcqUvv5AelsrAlq8epQfiG-o");
		NewsArticles articles = new NewsArticles("test", "您的客户某某某被拒绝,请查看!", "www.baidu.com", "http://mmbiz.qpic.cn/mmbiz_png/pGpcA7r0icbzFkCgS5ncDkqxtDYYGNXJTY0JLPfresvvrKeYCib0BRVu75B0fkicHiawPIaOSGhicAIK5oPzJHF3M9w/0");
		List<NewsArticles> articlesList = new ArrayList<NewsArticles>();
		articlesList.add(articles);
		NewsMessage msg = new NewsMessage("admin", null, null, "0",articlesList);
		MessageApi.sendNews(token, msg);
	}

}

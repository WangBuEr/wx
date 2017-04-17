package me.king.wx.qy.message.vo;

import java.util.List;

/**  
 * @Title:  		NewsMessage.java    
 * @Description:    news消息
 * @author: 		WangBuEr
 * @date:   		2017年4月18日 上午4:28:00   
 * @version 		V1.0
 */
public class NewsMessage extends Message {
	private String msgtype = "news";
	private News news;
	public NewsMessage() {
		super();
	}
	public NewsMessage(String touser, String toparty, String totag, String agentid,List<NewsArticles> articlesList) {
		super(touser, toparty, totag, agentid);
		setNews(articlesList);
	}
	class News{
		private List<NewsArticles> articles;
		
		public News() {
			super();
		}
		public News(List<NewsArticles> articles) {
			super();
			this.articles = articles;
		}

		public List<NewsArticles> getArticles() {
			return articles;
		}

		public void setArticles(List<NewsArticles> articles) {
			this.articles = articles;
		}
		
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public News getNews() {
		return news;
	}
	public void setNews(List<NewsArticles> articlesList) {
		this.news = new News(articlesList);
	}
	
}
